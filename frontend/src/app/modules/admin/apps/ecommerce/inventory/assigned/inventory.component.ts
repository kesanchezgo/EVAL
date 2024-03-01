import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { debounceTime, map, merge, Observable, Subject, switchMap, takeUntil } from 'rxjs';
import { fuseAnimations } from '@fuse/animations';
import { FuseConfirmationService } from '@fuse/services/confirmation';
import { InventoryBrand, InventoryCategory, InventoryPagination, InventoryProduct, InventoryTag, InventoryVendor } from 'app/modules/admin/apps/ecommerce/inventory/inventory.types';
import { ProjectResponse, Project, Pageable, Proyecto } from '../project.types';
import { ProjectEvaluation, ProjectEvaluationPageable,ProjectEvaluationContentItem, PaginationInfo } from '../project-evaluation.types';
import { InventoryService } from 'app/modules/admin/apps/ecommerce/inventory/inventory.service';
import { Criterion, Subcriterion } from '../criteria.types';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of } from 'rxjs';
import { User } from 'app/core/user/user.types';

@Component({
    selector       : 'inventory-assigned',
    templateUrl    : './inventory.component.html',
    styles         : [
        /* language=SCSS */
        `
            .inventory-grid {
                grid-template-columns: 48px auto 30px 30px 30px;

                @screen sm {
                    grid-template-columns: 48px auto 112px 112px 62px 62px;
                }

                @screen md {
                    grid-template-columns: 48px 112px auto 112px 112px 62px 62px;
                }

                @screen lg {
                    grid-template-columns: 48px 112px auto 112px 112px 96px 62px 62px 62px;
                }
            }
        `
    ],
    encapsulation  : ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
    animations     : fuseAnimations
})
export class InventoryAssignedComponent implements OnInit, AfterViewInit, OnDestroy
{
    @ViewChild(MatPaginator) private _paginator: MatPaginator;
    @ViewChild(MatSort) private _sort: MatSort;

    products$: Observable<InventoryProduct[]>;

    projects$: Observable<ProjectEvaluationContentItem[]>;
   
    criterias$: Observable<Criterion[]>;

    pageable: Pageable;
    
    brands: InventoryBrand[];
    categories: InventoryCategory[];
    filteredTags: InventoryTag[];
    flashMessage: 'success' | 'error' | null = null;
    isLoading: boolean = false;
    
    pagination: PaginationInfo;
    
    searchInputControl: FormControl = new FormControl();

    selectedProject: Proyecto | null = null;
    selectedProjectForm: FormGroup;
    
    tags: InventoryTag[];
    tagsEditMode: boolean = false;
    vendors: InventoryVendor[];
    private _unsubscribeAll: Subject<any> = new Subject<any>();

    user: User;
    evaluationForm: FormGroup;
    evaluationFinalForm: FormGroup;

    selectedSubcriteria: Subcriterion | null = null;
    /**
     * Constructor
     */
    constructor(
        private _changeDetectorRef: ChangeDetectorRef,
        private _fuseConfirmationService: FuseConfirmationService,
        private _formBuilder: FormBuilder,
        private _inventoryService: InventoryService,
        private _snackBar: MatSnackBar
    )
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {
        const userString = localStorage.getItem('user');

        // Verificar si el usuario existe en el localStorage
        if (userString) {
            // Deserializar la cadena JSON a un objeto JavaScript
            const user = JSON.parse(userString);
            this.user = user;
            // Ahora puedes acceder a las propiedades del usuario
            console.log("list");
            console.log(user.id);
            console.log(user.name);
            console.log(user.avatar);
            console.log(user.email);
            console.log(user.roles);
        } else {
            console.log('No se encontró ningún usuario en el localStorage');
        }
        // Create the selected product form
        this.selectedProjectForm = this._formBuilder.group({
            id               : [],
            id_evaluacion               : [],
            id_proyecto               : [''],
            principal                 : [''],
            estado_proyecto           : [''],
            estado_contrato           : [''],
            monto_cont           : [''],
            claves_proy           : [''],
            resumen_ejecutivo           : [''],
            obj_general_proy           : [''],
            est_proy           : [''],
            impacto_proy           : [''],
            resul_espe_proy           : [''],
            hipo_proy           : [''],
            just_proy           : [''],
            fech_ini_proy           : [''],
            integrantes           : [''],
            facultades           : [''],
            departamentos           : [''],
            id_area_prioritaria           : [''],
            id_area_investigacion           : [''],
            id_linea_investigacion           : [''],
            nomb_areapri           : [''],
            est_cont           : [''],
            area_investigacion           : [''],
            linea_investigacion           : [''],
            id_anio           : [''],
            tiposubven           : [''],
            dni_principal           : [''],
            area_ocde           : [''],
            sub_area_ocde           : [''],
            disciplina_ocde           : [''],
            fase_estado_proyecto           : [''],
            fech_ini_cont           : [''],
            fech_fin_cont           : ['']
        });

        this.evaluationForm = this._formBuilder.group({
            // Agrega más campos de formulario según sea necesario para otros criterios
          });

        this.evaluationFinalForm = this._formBuilder.group({
            projectEvaluation: ['', Validators.required],
            subcriterion: ['', Validators.required],
            score: ['', Validators.required]
        });

        // Get the pagination
        this._inventoryService.projectEvaluationPaginationInfo$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe((pagination: PaginationInfo) => {

                // Update the pagination
                this.pagination = pagination;
                console.log("pagination:", this.pagination);
                // Mark for check
                this._changeDetectorRef.markForCheck();
            });


        // Get the pageable
        this._inventoryService.projectEvaluationPageable$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe((pageable: Pageable) => {

                // Update the pageable
                this.pageable = pageable;
                console.log("pageable:", this.pageable);
                // Mark for check
                this._changeDetectorRef.markForCheck();
            });

        // Get the projects
        

        this._inventoryService.getProjects(0, 10, 'project.externalId', 'asc', '', 1,  this.user.id.toString(),'A')
        .subscribe(() => {
            this.isLoading = false;
            
        });
        //opción para que no se vuelva a llamar
        this.projects$ = this._inventoryService.projectEvaluations$;
        console.log("projects:", this.projects$)


        // Get the criterias
        this.criterias$ = this._inventoryService.criterias$;
        console.log("criterias:", this.criterias$)
        this.criterias$.subscribe(criterias => {
            criterias.forEach(criteria => {
                this.evaluationForm.addControl(criteria.id.toString(), this._formBuilder.control('', Validators.required));
                this.evaluationForm.addControl(criteria.id.toString() + '_score', this._formBuilder.control('', Validators.required)); // Agregar el FormControl para el score
            });
        });
       
        // Subscribe to search input field value changes
        this.searchInputControl.valueChanges
            .pipe(
                takeUntil(this._unsubscribeAll),
                debounceTime(300),
                switchMap((query) => {
                    this.closeDetails();
                    this.isLoading = true;
                    return this._inventoryService.getProjects(0, 10, 'project.externalId', 'asc', query,1, this.user.id.toString(),'A');
                }),
                map(() => {
                    this.isLoading = false;
                    this._changeDetectorRef.markForCheck();
                })
            )
            .subscribe();
    }

    /**
     * After view init
     */
    ngAfterViewInit(): void
    {
        if ( this._sort && this._paginator )
        {
            // Set the initial sort
            this._sort.sort({
                id          : 'project.externalId',
                start       : 'asc',
                disableClear: true
            });

            // Mark for check
            this._changeDetectorRef.markForCheck();

            // If the user changes the sort order...
            this._sort.sortChange
                .pipe(takeUntil(this._unsubscribeAll))
                .subscribe(() => {
                    // Reset back to the first page
                    this._paginator.pageIndex = 0;

                    // Close the details
                    this.closeDetails();
                });

            // Get products if sort or page changes
            merge(this._sort.sortChange, this._paginator.page).pipe(
                switchMap(() => {
                    this.closeDetails();
                    this.isLoading = true;
                    return this._inventoryService.getProjects(this._paginator.pageIndex, this._paginator.pageSize, this._sort.active, this._sort.direction,'',1, this.user.id.toString(),'A');
                }),
                map(() => {
                    this.isLoading = false;
                })
            ).subscribe();
        }
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void
    {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Toggle project details
     *
     * @param projectId
     */
    toggleDetails(projectId: string, evaluationId:number, id:number): void
    {
        var number1 = this.getCodeNumberSISMO(projectId);
        // If the product is already selected...
        console.log("number: ",number1);
        console.log("selectedProject.id_proyecto: ",this.selectedProject?.id_proyecto);
        if ( this.selectedProject && this.selectedProject.id_proyecto === number1 )
        {
            // Close the details
            console.log("cerre");
            this.closeDetails();
            return;
        }

        //console.log("selectedproject: ", this.selectedProject);
        // Get the product by id
        this._inventoryService.getProjectById(number1)
            .subscribe((project) => {

                // Set the selected product
                this.selectedProject = project;
                console.log("proyecto: ",this.selectedProject);
                console.log("projectId: ",projectId);
                console.log("evaluationId: ",evaluationId);
                console.log("id: ",id);
                // Fill the form
                this.selectedProjectForm.patchValue(project);
                this.selectedProjectForm.patchValue({
                    id_evaluacion: evaluationId,
                    id: id
                  })

                // Mark for check
                this._changeDetectorRef.markForCheck();
            });
    }

    getCodeNumberSISMO(code: string): string | null {
        const regex = /^SISMO-(\d+)$/;
        const match = code.match(regex);
        if (match && match[1]) {
            return match[1];
        } else {
            console.log("Could not obtain code");
            return null;
        }
    }

    /**
     * Close the details
     */
    closeDetails(): void
    {
        this.selectedProject = null;
    }

    /**
     * Toggle the tags edit mode
     */
    toggleTagsEditMode(): void
    {
        this.tagsEditMode = !this.tagsEditMode;
    }
    /**
     * Show flash message
     */
    showFlashMessage(type: 'success' | 'error'): void
    {
        // Show the message
        this.flashMessage = type;

        // Mark for check
        this._changeDetectorRef.markForCheck();

        // Hide it after 3 seconds
        setTimeout(() => {

            this.flashMessage = null;

            // Mark for check
            this._changeDetectorRef.markForCheck();
        }, 3000);
    }

    /**
     * Track by function for ngFor loops
     *
     * @param index
     * @param item
     */
    trackByFn(index: number, item: any): any
    {
        return item.id || index;
    }

    submitEvaluation() {
        console.log("formPry: ",this.selectedProjectForm.value); // Solo para propósitos de demostración
        console.log("formEval: ",this.evaluationForm.value); // Solo para propósitos de demostración

         // Obtener el id_evaluacion del primer formulario
            const id_evaluacion = this.selectedProjectForm.value.id_evaluacion;

        // Crear un array para almacenar los objetos de subcriterios con sus id y score
            const subcriterios = [];

        // Iterar sobre los elementos del segundo formulario
        for (const key in this.evaluationForm.value) {
            // Verificar si el elemento es un subcriterio (tiene un id numérico)
            if (!isNaN(parseInt(key))) {
                const projectEvaluation = id_evaluacion;
                const subcriterion = this.evaluationForm.value[key].id;
                const score = this.evaluationForm.value[`${key}_score`];
                // Verificar si score es un número antes de agregarlo
                if (!isNaN(parseFloat(score))) {
                    subcriterios.push({ projectEvaluation, subcriterion, score });
                } else {
                    console.error(`El score para el subcriterio ${subcriterion} no es un número válido.`);
                }
            }
        }
        
        console.log("formEvalF: ",subcriterios);

          // Open the confirmation dialog
        const confirmation = this._fuseConfirmationService.open({
            title  : 'Confirmar envío de evaluación',
            message: '¿Estás seguro de que deseas enviar esta evaluación? Esta acción no se puede deshacer.',
            actions: {
                confirm: {
                    label: 'Enviar'
                }
            }
        });

        confirmation.afterClosed().subscribe((result) => {
            // Si se presiona el botón de confirmación...
            if (result === 'confirmed') {
                // Obtener el objeto del producto
                const product = this.selectedProjectForm.getRawValue();
                // Enviar la evaluación al servidor
                this._inventoryService.registerEvaluation(subcriterios).subscribe(
                    () => {
                        // Mostrar un mensaje de éxito
                        this._snackBar.open('La evaluación se ha enviado exitosamente', 'Cerrar', {
                            duration: 3000 // Duración en milisegundos
                        });
                        
                        // Limpiar los campos del formulario de evaluación
                        this.evaluationForm.reset();
                        
                        // Aquí colocas la lógica que deseas ejecutar después de enviar la evaluación
                        this.closeDetails();
                        this.isLoading = true;
        
                        // Llamada al servicio para obtener los proyectos actualizados
                        this._inventoryService.getProjects(this._paginator.pageIndex, this._paginator.pageSize, this._sort.active, this._sort.direction,"",1,this.user.id.toString(),'A')
                            .subscribe(() => {
                                this.isLoading = false;
                            });
                    },
                    (error) => {
                        console.error('Error al enviar la evaluación:', error);
                        this._snackBar.open('Hubo un error al enviar la evaluación. Por favor, inténtalo de nuevo más tarde', 'Cerrar', {
                            duration: 3000
                        });
                    }
                );
            }
        });
        
      }

    onSubcriterionChange(subcriteria: Subcriterion, criteriaId:number): void {
        this.selectedSubcriteria = subcriteria;
      
        // Obtener el formControl correspondiente al puntaje del criterio actual
        const scoreFormControl = this.evaluationForm.get(criteriaId + '_score');
      
        // Actualizar los rangos mínimos y máximos del input
        scoreFormControl?.setValidators([Validators.required, Validators.min(subcriteria.range1), Validators.max(subcriteria.range2)]);
        scoreFormControl?.updateValueAndValidity();
      }
      
}
