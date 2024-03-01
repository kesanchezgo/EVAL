import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { FuseMediaWatcherService } from '@fuse/services/media-watcher';
import { FuseNavigationService, FuseVerticalNavigationComponent } from '@fuse/components/navigation';
import { Navigation } from 'app/core/navigation/navigation.types';
import { NavigationService } from 'app/core/navigation/navigation.service';
import { User } from 'app/core/user/user.types';
import { UserService } from 'app/core/user/user.service';

@Component({
    selector     : 'classy-layout',
    templateUrl  : './classy.component.html',
    encapsulation: ViewEncapsulation.None
})
export class ClassyLayoutComponent implements OnInit, OnDestroy
{
    isScreenSmall: boolean;
    navigation: Navigation;
    user: User;
    private _unsubscribeAll: Subject<any> = new Subject<any>();

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private _router: Router,
        private _navigationService: NavigationService,
        private _userService: UserService,
        private _fuseMediaWatcherService: FuseMediaWatcherService,
        private _fuseNavigationService: FuseNavigationService,
        private _changeDetectorRef: ChangeDetectorRef
    )
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Getter for current year
     */
    get currentYear(): number
    {
        return new Date().getFullYear();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {
        

        // Subscribe to the user service
        this._userService.user$
            .pipe((takeUntil(this._unsubscribeAll)))
            .subscribe((user: User) => {
                this.user = user;
                 // Mark for check
                 console.log("classy component: ", this.user);
                 this._changeDetectorRef.markForCheck();
            });

            const userString = localStorage.getItem('user');

            // Verificar si el usuario existe en el localStorage
            if (userString) {
                // Deserializar la cadena JSON a un objeto JavaScript
                const user = JSON.parse(userString);
                this.user = user;
                // Ahora puedes acceder a las propiedades del usuario
                console.log(user.id);
                console.log(user.name);
                console.log(user.avatar);
                console.log(user.email);
                console.log(user.roles);
            } else {
                console.log('No se encontró ningún usuario en el localStorage');
            }


            // Subscribe to navigation data
            this._navigationService.navigation$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe((navigation: Navigation) => {
                this.navigation = navigation;

                // Verificar si el usuario tiene el rol de administrador
                const isAdmin = this.user.roles.includes('ROLE_ADMIN');

                // Si el usuario es administrador, mostrar toda la navegación
                if (isAdmin) {
                    // No se necesita ninguna restricción, todos los elementos se muestran
                    console.log("es admin");
                } else {
                    // Si el usuario no es administrador, restringir la navegación
                    // Filtrar los elementos de navegación que el usuario no tiene permiso para ver
                    this.navigation.default.forEach(group => {
                        if (group.children) {
                            group.children = group.children.filter(item => {
                                // Aquí especifica las restricciones de acceso basadas en los roles
                                // Por ejemplo, si solo los usuarios con el rol 'ROLE_ADMIN' pueden ver todos los elementos, puedes ocultar los elementos no deseados
                                return item.id === 'apps.ecommerce.assigned' || item.id === 'apps.ecommerce.reviewed';
                            });
                        }
                    });
                }
            });
            

                    // Subscribe to media changes
        this._fuseMediaWatcherService.onMediaChange$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(({matchingAliases}) => {

                // Check if the screen is small
                this.isScreenSmall = !matchingAliases.includes('md');
            });
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
     * Toggle navigation
     *
     * @param name
     */
    toggleNavigation(name: string): void
    {
        // Get the navigation
        const navigation = this._fuseNavigationService.getComponent<FuseVerticalNavigationComponent>(name);

        if ( navigation )
        {
            // Toggle the opened status
            navigation.toggle();
        }
    }
}
