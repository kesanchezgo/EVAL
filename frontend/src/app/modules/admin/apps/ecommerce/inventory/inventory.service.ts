import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, filter, map, Observable, of, switchMap, take, tap, throwError,catchError } from 'rxjs';
import { InventoryBrand, InventoryCategory, InventoryPagination, InventoryProduct, InventoryTag, InventoryVendor } from 'app/modules/admin/apps/ecommerce/inventory/inventory.types';
import {Project, ProjectResponse, Pageable, Proyecto  } from './project.types';
import { Criterion, CriteriaResponse } from './criteria.types';
import { Sector, SectorResponse } from './sector.types';
import { ProjectEvaluationContentItem, ProjectEvaluationResponse, ProjectEvaluationPageable, PaginationInfo } from './project-evaluation.types';
import { environment } from 'environments/environment';import { isNullBlankZeroUndefined } from 'app/core/util/ValidateData';
import { LineResearch, LineResearchResponse } from './line-research.types';
;
@Injectable({
    providedIn: 'root'
})
export class InventoryService
{
    // Private
    private _brands: BehaviorSubject<InventoryBrand[] | null> = new BehaviorSubject(null);
    private _categories: BehaviorSubject<InventoryCategory[] | null> = new BehaviorSubject(null);
    private _pagination: BehaviorSubject<InventoryPagination | null> = new BehaviorSubject(null);
    private _product: BehaviorSubject<InventoryProduct | null> = new BehaviorSubject(null);
    private _products: BehaviorSubject<InventoryProduct[] | null> = new BehaviorSubject(null);

    /* private _ppagination: BehaviorSubject<Pagination | null> = new BehaviorSubject(null);
    private _projects: BehaviorSubject<Project[] | null> = new BehaviorSubject(null);
 */

    
    private _projects: BehaviorSubject<Project[]> = new BehaviorSubject<Project[]>(null);
    private _pageable: BehaviorSubject<Pageable> = new BehaviorSubject<Pageable>(null);


    private _projectEvaluations: BehaviorSubject<ProjectEvaluationContentItem[]> = new BehaviorSubject<ProjectEvaluationContentItem[]>(null);
    private _pageableEvaluations: BehaviorSubject<ProjectEvaluationPageable> = new BehaviorSubject<ProjectEvaluationPageable>(null);
    private _paginationInfoEvaluations: BehaviorSubject<PaginationInfo> = new BehaviorSubject<PaginationInfo>(null);

    private _proyecto: BehaviorSubject<Proyecto | null> = new BehaviorSubject(null);
    private _proyectos: BehaviorSubject<Proyecto[]> = new BehaviorSubject<Proyecto[]>(null);

    private _criterias: BehaviorSubject<Criterion[]> = new BehaviorSubject<Criterion[]>(null);

    private _sectors: BehaviorSubject<Sector[]> = new BehaviorSubject<Sector[]>(null);

    private _report: BehaviorSubject<string> = new BehaviorSubject<string>(null);

    private _line_researchs: BehaviorSubject<LineResearch[]> = new BehaviorSubject<LineResearch[]>(null);

    private _tags: BehaviorSubject<InventoryTag[] | null> = new BehaviorSubject(null);
    private _vendors: BehaviorSubject<InventoryVendor[] | null> = new BehaviorSubject(null);

    /**
     * Constructor
     */
    constructor(private _httpClient: HttpClient)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Getter for brands
     */
    get brands$(): Observable<InventoryBrand[]>
    {
        return this._brands.asObservable();
    }

    /**
     * Getter for categories
     */
    get categories$(): Observable<InventoryCategory[]>
    {
        return this._categories.asObservable();
    }

    /**
     * Getter for pagination
     */
    get pagination$(): Observable<InventoryPagination>
    {
        return this._pagination.asObservable();
    }

    /**
     * Getter for product
     */
    get product$(): Observable<InventoryProduct>
    {
        return this._product.asObservable();
    }

    /**
     * Getter for product
     */
    get proyecto$(): Observable<Proyecto>
    {
        return this._proyecto.asObservable();
    }

    /**
     * Getter for products
     */
    get products$(): Observable<InventoryProduct[]>
    {
        return this._products.asObservable();
    }


    /* get pageable$(): Observable<Pageable>
    {
        return this._pageable.asObservable();
    }


    get projects$(): Observable<Project[]>
    {
        return this._projects.asObservable();
    } */

    get projectEvaluationPaginationInfo$(): Observable<PaginationInfo>
    {
        return this._paginationInfoEvaluations.asObservable();
    }

    get projectEvaluationPageable$(): Observable<ProjectEvaluationPageable>
    {
        return this._pageableEvaluations.asObservable();
    }


    get projectEvaluations$(): Observable<ProjectEvaluationContentItem[]>
    {
        return this._projectEvaluations.asObservable();
    }

    get criterias$(): Observable<Criterion[]>
    {
        return this._criterias.asObservable();
    }

    get sectors$(): Observable<Sector[]>
    {
        return this._sectors.asObservable();
    }

    get report$(): Observable<string>
    {
        return this._report.asObservable();
    }

    get lineResearchs$(): Observable<Sector[]>
    {
        return this._line_researchs.asObservable();
    }

    /**
     * Getter for tags
     */
    get tags$(): Observable<InventoryTag[]>
    {
        return this._tags.asObservable();
    }

    /**
     * Getter for vendors
     */
    get vendors$(): Observable<InventoryVendor[]>
    {
        return this._vendors.asObservable();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Get brands
     */
    getBrands(): Observable<InventoryBrand[]>
    {
        return this._httpClient.get<InventoryBrand[]>('api/apps/ecommerce/inventory/brands').pipe(
            tap((brands) => {
                this._brands.next(brands);
            })
        );
    }

    /**
     * Get categories
     */
    getCategories(): Observable<InventoryCategory[]>
    {
        return this._httpClient.get<InventoryCategory[]>('api/apps/ecommerce/inventory/categories').pipe(
            tap((categories) => {
                this._categories.next(categories);
            })
        );
    }

    /**
     * Get products
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
    getProducts(page: number = 0, size: number = 10, sort: string = 'name', order: 'asc' | 'desc' | '' = 'asc', search: string = ''):
        Observable<{ pagination: InventoryPagination; products: InventoryProduct[] }>
    {
        return this._httpClient.get<{ pagination: InventoryPagination; products: InventoryProduct[] }>('api/apps/ecommerce/inventory/products', {
            params: {
                page: '' + page,
                size: '' + size,
                sort,
                order,
                search
            }
        }).pipe(
            tap((response) => {
                this._pagination.next(response.pagination);
                this._products.next(response.products);
                console.log("jsonPro: ",response.products);
                console.log("jsonPag: ",response.pagination);
            })
        );
    }


    getProjects(
        page: number = 0,
        size: number = 10,
        sort: string = 'project.externalId',
        order: 'asc' | 'desc' | '' = 'asc',
        search: string = '',
        evaluation:number = 1,
        userId: string = '',
        condition: string =''
      ): Observable<ProjectEvaluationResponse> {
        return this._httpClient.get<ProjectEvaluationResponse>(environment.evalsys+'api/projectevaluation/project-evaluations', {
          params: {
            page: '' + page,
            size: '' + size,
            sort,
            order,
            search,
            evaluation,
            userId,
            condition
          }
        }).pipe(
          tap((response: ProjectEvaluationResponse) => {
            this._projectEvaluations.next(response.content);
            this._pageableEvaluations.next(response.pageable);
            this._paginationInfoEvaluations.next({
                last: response.last,
                totalPages: response.totalPages,
                totalElements: response.totalElements,
                first: response.first,
                size: response.size,
                number: response.number,
                sort: response.sort,
                numberOfElements: response.numberOfElements,
                empty: response.empty
              });
            console.log("Pro: ",response.content);
            console.log("Pag: ",response.pageable);
            console.log("PInfo: ",this._paginationInfoEvaluations.value);
          })
        );
      }


      getCriterias(
        page: number = 0,
        size: number = 10,
        sort: string = 'name',
        order: 'asc' | 'desc' | '' = 'asc',
        search: string = ''
      ): Observable<CriteriaResponse> {
        return this._httpClient.get<CriteriaResponse>(environment.evalsys+'api/evaluations/1/criteria', {
          /* params: {
            page: '' + page,
            size: '' + size,
            sort,
            order,
            search
          } */
        }).pipe(
          tap((response: CriteriaResponse) => {
            this._criterias.next(response);
            console.log("Crit: ",response);
          })
        );
      }


      getSectors(
        page: number = 0,
        size: number = 10,
        sort: string = 'name',
        order: 'asc' | 'desc' | '' = 'asc',
        search: string = ''
      ): Observable<SectorResponse> {
        return this._httpClient.get<SectorResponse>(environment.evalsys+'api/sectors', {
          /* params: {
            page: '' + page,
            size: '' + size,
            sort,
            order,
            search
          } */
        }).pipe(
          tap((response: SectorResponse) => {
            this._sectors.next(response);
            console.log("Sectors: ",response);
          })
        );
      }

      getLineResearchs(
        page: number = 0,
        size: number = 10,
        sort: string = 'name',
        order: 'asc' | 'desc' | '' = 'asc',
        search: string = ''
      ): Observable<LineResearchResponse> {
        return this._httpClient.get<LineResearchResponse>(environment.evalsys+'api/line_researches', {
          /* params: {
            page: '' + page,
            size: '' + size,
            sort,
            order,
            search
          } */
        }).pipe(
          tap((response: LineResearchResponse) => {
            this._line_researchs.next(response);
            console.log("LineResearch: ",response);
          })
        );
      }



      getRolByEmail(
        email:string
      ):
        Observable<any>
      {
        let url = environment.evalsys + `api/users/roles/${email}`;
      
       /*  if (!isNullBlankZeroUndefined(email)){
            url = url + `&correo=${email}`;
        } */
        return this._httpClient.get<any>(url);
      }

      
    /**
     * Get product by id
     */
    getProductById(id: string): Observable<InventoryProduct>
    {
        return this._products.pipe(
            take(1),
            map((products) => {

                // Find the product
                const product = products.find(item => item.id === id) || null;

                // Update the product
                this._product.next(product);

                // Return the product
                return product;
            }),
            switchMap((product) => {

                if ( !product )
                {
                    return throwError('Could not found product with id of ' + id + '!');
                }

                return of(product);
            })
        );
    }

    /* getProjectById(id: string): Observable<Proyecto> {
        return this._httpClient.get<Proyecto>('http://vri.gestioninformacion.unsa.edu.pe/codeigniter/index.php/duginf/proyecto/' + id).pipe(
            map((proyecto) => {
                // Update the producto
                this._proyecto.next(proyecto);
                return proyecto;
            }),
            catchError((error) => {
                // Handle error
                return throwError('Could not fetch proyecto with id ' + id);
            })
        );
    }
 */
    getReport(id: string): Observable<any> {
        const headers = new HttpHeaders()
          .set('Accept', 'application/json');
    
        return this._httpClient.get<any>(environment.evalsys + 'api/generate-report', {
          headers: headers,
          params: new HttpParams().set('projectId', id),
          responseType: 'text' as 'json'
        }).pipe(
          map((response: any) => {
            // Check if response is JSON
            if (response && typeof response === 'object') {
              // Handle JSON response
              console.log("Report JSON: ", response);
              return response;
            } else {
              // Handle non-JSON response (e.g., PDF)
              console.log("Report PDF URL: ", response);
              // You can redirect to the PDF URL or do any other necessary processing
              //window.open(response, '_blank');
              // Returning null as we don't have a JSON response
              return response;
            }
          }),
          catchError((error) => {
            console.error('Error fetching report:', error);
            return throwError('Could not fetch report with ID ' + id);
          })
        );
      }

    getProjectById(id: string): Observable<Proyecto> {
        // Make the HTTP GET request to fetch the project by ID
        return this._httpClient.get<Project[]>('http://vri.gestioninformacion.unsa.edu.pe/codeigniter/index.php/duginf/proyecto/' + id).pipe(
            map((proyectos: any[]) => {
                // Check if there are any projects in the array
                if (proyectos && proyectos.length > 0) {
                    // Get the first project from the array
                    const proyecto = proyectos[0];
                    // Update the project
                    this._proyecto.next(proyecto);
                    // Return the project
                    return proyecto;
                } else {
                    // If no project is found, return null or throw an error
                    return throwError('No project found with ID ' + id);
                }
            }),
            catchError((error) => {
                // Handle the error and return an error message
                return throwError('Could not fetch project with ID ' + id);
            })
        );
    }


    /**
     * Create product
     */
    createProduct(): Observable<InventoryProduct>
    {
        return this.products$.pipe(
            take(1),
            switchMap(products => this._httpClient.post<InventoryProduct>('api/apps/ecommerce/inventory/product', {}).pipe(
                map((newProduct) => {

                    // Update the products with the new product
                    this._products.next([newProduct, ...products]);

                    // Return the new product
                    return newProduct;
                })
            ))
        );
    }

    /**
     * Update product
     *
     * @param id
     * @param product
     */
    updateProduct(id: string, product: InventoryProduct): Observable<InventoryProduct>
    {
        return this.products$.pipe(
            take(1),
            switchMap(products => this._httpClient.patch<InventoryProduct>('api/apps/ecommerce/inventory/product', {
                id,
                product
            }).pipe(
                map((updatedProduct) => {

                    // Find the index of the updated product
                    const index = products.findIndex(item => item.id === id);

                    // Update the product
                    products[index] = updatedProduct;

                    // Update the products
                    this._products.next(products);

                    // Return the updated product
                    return updatedProduct;
                }),
                switchMap(updatedProduct => this.product$.pipe(
                    take(1),
                    filter(item => item && item.id === id),
                    tap(() => {

                        // Update the product if it's selected
                        this._product.next(updatedProduct);

                        // Return the updated product
                        return updatedProduct;
                    })
                ))
            ))
        );
    }

    registerEvaluation(criterions: any[]): Observable<any[]> {
        return this._httpClient.post<any[]>(environment.evalsys+'api/subcriterion-scores/multiple', criterions);
    }

    /**
     * Delete the product
     *
     * @param id
     */
    deleteProduct(id: string): Observable<boolean>
    {
        return this.products$.pipe(
            take(1),
            switchMap(products => this._httpClient.delete('api/apps/ecommerce/inventory/product', {params: {id}}).pipe(
                map((isDeleted: boolean) => {

                    // Find the index of the deleted product
                    const index = products.findIndex(item => item.id === id);

                    // Delete the product
                    products.splice(index, 1);

                    // Update the products
                    this._products.next(products);

                    // Return the deleted status
                    return isDeleted;
                })
            ))
        );
    }

    /**
     * Get tags
     */
    getTags(): Observable<InventoryTag[]>
    {
        return this._httpClient.get<InventoryTag[]>('api/apps/ecommerce/inventory/tags').pipe(
            tap((tags) => {
                this._tags.next(tags);
            })
        );
    }

    /**
     * Create tag
     *
     * @param tag
     */
    createTag(tag: InventoryTag): Observable<InventoryTag>
    {
        return this.tags$.pipe(
            take(1),
            switchMap(tags => this._httpClient.post<InventoryTag>('api/apps/ecommerce/inventory/tag', {tag}).pipe(
                map((newTag) => {

                    // Update the tags with the new tag
                    this._tags.next([...tags, newTag]);

                    // Return new tag from observable
                    return newTag;
                })
            ))
        );
    }

    /**
     * Update the tag
     *
     * @param id
     * @param tag
     */
    updateTag(id: string, tag: InventoryTag): Observable<InventoryTag>
    {
        return this.tags$.pipe(
            take(1),
            switchMap(tags => this._httpClient.patch<InventoryTag>('api/apps/ecommerce/inventory/tag', {
                id,
                tag
            }).pipe(
                map((updatedTag) => {

                    // Find the index of the updated tag
                    const index = tags.findIndex(item => item.id === id);

                    // Update the tag
                    tags[index] = updatedTag;

                    // Update the tags
                    this._tags.next(tags);

                    // Return the updated tag
                    return updatedTag;
                })
            ))
        );
    }

    /**
     * Delete the tag
     *
     * @param id
     */
    deleteTag(id: string): Observable<boolean>
    {
        return this.tags$.pipe(
            take(1),
            switchMap(tags => this._httpClient.delete('api/apps/ecommerce/inventory/tag', {params: {id}}).pipe(
                map((isDeleted: boolean) => {

                    // Find the index of the deleted tag
                    const index = tags.findIndex(item => item.id === id);

                    // Delete the tag
                    tags.splice(index, 1);

                    // Update the tags
                    this._tags.next(tags);

                    // Return the deleted status
                    return isDeleted;
                }),
                filter(isDeleted => isDeleted),
                switchMap(isDeleted => this.products$.pipe(
                    take(1),
                    map((products) => {

                        // Iterate through the contacts
                        products.forEach((product) => {

                            const tagIndex = product.tags.findIndex(tag => tag === id);

                            // If the contact has the tag, remove it
                            if ( tagIndex > -1 )
                            {
                                product.tags.splice(tagIndex, 1);
                            }
                        });

                        // Return the deleted status
                        return isDeleted;
                    })
                ))
            ))
        );
    }

    /**
     * Get vendors
     */
    getVendors(): Observable<InventoryVendor[]>
    {
        return this._httpClient.get<InventoryVendor[]>('api/apps/ecommerce/inventory/vendors').pipe(
            tap((vendors) => {
                this._vendors.next(vendors);
            })
        );
    }

    /**
     * Update the avatar of the given contact
     *
     * @param id
     * @param avatar
     */
    /*uploadAvatar(id: string, avatar: File): Observable<Contact>
    {
        return this.contacts$.pipe(
            take(1),
            switchMap(contacts => this._httpClient.post<Contact>('api/apps/contacts/avatar', {
                id,
                avatar
            }, {
                headers: {
                    'Content-Type': avatar.type
                }
            }).pipe(
                map((updatedContact) => {

                    // Find the index of the updated contact
                    const index = contacts.findIndex(item => item.id === id);

                    // Update the contact
                    contacts[index] = updatedContact;

                    // Update the contacts
                    this._contacts.next(contacts);

                    // Return the updated contact
                    return updatedContact;
                }),
                switchMap(updatedContact => this.contact$.pipe(
                    take(1),
                    filter(item => item && item.id === id),
                    tap(() => {

                        // Update the contact if it's selected
                        this._contact.next(updatedContact);

                        // Return the updated contact
                        return updatedContact;
                    })
                ))
            ))
        );
    }*/
}
