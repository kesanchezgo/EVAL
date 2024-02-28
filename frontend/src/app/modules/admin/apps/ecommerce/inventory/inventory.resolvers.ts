import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { InventoryService } from 'app/modules/admin/apps/ecommerce/inventory/inventory.service';
import { InventoryBrand, InventoryCategory, InventoryPagination, InventoryProduct, InventoryTag, InventoryVendor } from 'app/modules/admin/apps/ecommerce/inventory/inventory.types';
import { ProjectResponse } from './project.types';import { CriteriaResponse } from './criteria.types';
import { ProjectEvaluationResponse } from './project-evaluation.types';
;

@Injectable({
    providedIn: 'root'
})
export class InventoryBrandsResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InventoryBrand[]>
    {
        return this._inventoryService.getBrands();
    }
}

@Injectable({
    providedIn: 'root'
})
export class InventoryCategoriesResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InventoryCategory[]>
    {
        return this._inventoryService.getCategories();
    }
}

@Injectable({
    providedIn: 'root'
})
export class InventoryProductResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(
        private _inventoryService: InventoryService,
        private _router: Router
    )
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InventoryProduct>
    {
        return this._inventoryService.getProductById(route.paramMap.get('id'))
                   .pipe(
                       // Error here means the requested product is not available
                       catchError((error) => {

                           // Log the error
                           console.error(error);

                           // Get the parent url
                           const parentUrl = state.url.split('/').slice(0, -1).join('/');

                           // Navigate to there
                           this._router.navigateByUrl(parentUrl);

                           // Throw an error
                           return throwError(error);
                       })
                   );
    }
}


@Injectable({
    providedIn: 'root'
})
export class InventoryProjectResolver implements Resolve<any> {
    constructor(private proyectoService: InventoryService, private router: Router) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
        const projectId = route.paramMap.get('id');
        
        return this.proyectoService.getProjectById(projectId).pipe(
            catchError(error => {
                console.error(error);
                const parentUrl = state.url.split('/').slice(0, -1).join('/');
                this.router.navigateByUrl(parentUrl);
                return throwError('Could not fetch proyecto with id ' + projectId);
            })
        );
    }
}

@Injectable({
    providedIn: 'root'
})
export class InventoryProductsResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<{ pagination: InventoryPagination; products: InventoryProduct[] }>
    {
        return this._inventoryService.getProducts();
    }
}

@Injectable({
    providedIn: 'root'
})
export class InventoryProjectsResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProjectEvaluationResponse>
    {
        // Aquí llamas al método getProjects de tu servicio
        return this._inventoryService.getProjects();
    }
}


@Injectable({
    providedIn: 'root'
})
export class InventoryCriteriasResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CriteriaResponse>
    {
        // Aquí llamas al método getProjects de tu servicio
        return this._inventoryService.getCriterias();
    }
}

@Injectable({
    providedIn: 'root'
})
export class InventoryTagsResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InventoryTag[]>
    {
        return this._inventoryService.getTags();
    }
}

@Injectable({
    providedIn: 'root'
})
export class InventoryVendorsResolver implements Resolve<any>
{
    /**
     * Constructor
     */
    constructor(private _inventoryService: InventoryService)
    {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param route
     * @param state
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<InventoryVendor[]>
    {
        return this._inventoryService.getVendors();
    }
}
