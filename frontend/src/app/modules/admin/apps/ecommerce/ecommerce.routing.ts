import { Route } from '@angular/router';
import { InventoryComponent } from 'app/modules/admin/apps/ecommerce/inventory/inventory.component';
import { InventoryListComponent } from 'app/modules/admin/apps/ecommerce/inventory/list/inventory.component';
import { InventoryBrandsResolver, InventoryCategoriesResolver, InventoryProductsResolver, InventoryTagsResolver, InventoryVendorsResolver, InventoryProjectsResolver, InventoryProjectResolver, InventoryCriteriasResolver, InventorySectorsResolver, InventoryLineResearchsResolver } from 'app/modules/admin/apps/ecommerce/inventory/inventory.resolvers';
import { InventoryAssignedComponent } from './inventory/assigned/inventory.component';
import { InventoryReviewedComponent } from './inventory/reviewed/inventory.component';
export const ecommerceRoutes: Route[] = [
    {
        path      : '',
        pathMatch : 'full',
        redirectTo: 'projects'
    },
    {
        path     : 'projects',
        component: InventoryComponent,
        children : [
            {
                path     : 'all',
                component: InventoryListComponent,
                resolve  : {
                    brands    : InventoryBrandsResolver,
                    categories: InventoryCategoriesResolver,
                    products  : InventoryProductsResolver,
                    projects  : InventoryProjectsResolver,
                    criterias : InventoryCriteriasResolver,
                    /* project   : InventoryProjectResolver, */
                    tags      : InventoryTagsResolver,
                    vendors   : InventoryVendorsResolver
                }
            },

            {
                path     : 'assigned',
                component: InventoryAssignedComponent,
                resolve  : {
                    brands    : InventoryBrandsResolver,
                    categories: InventoryCategoriesResolver,
                    products  : InventoryProductsResolver,
                    projects  : InventoryProjectsResolver,
                    criterias : InventoryCriteriasResolver,
                    sectors : InventorySectorsResolver,
                    lineResearchs: InventoryLineResearchsResolver,
                    /* project   : InventoryProjectResolver, */
                    tags      : InventoryTagsResolver,
                    vendors   : InventoryVendorsResolver
                }
            },
            {
                path     : 'reviewed',
                component: InventoryReviewedComponent,
                resolve  : {
                    brands    : InventoryBrandsResolver,
                    categories: InventoryCategoriesResolver,
                    products  : InventoryProductsResolver,
                    projects  : InventoryProjectsResolver,
                    criterias : InventoryCriteriasResolver,
                    /* project   : InventoryProjectResolver, */
                    tags      : InventoryTagsResolver,
                    vendors   : InventoryVendorsResolver
                }
            }

        ]
        /*children : [
            {
                path     : '',
                component: ContactsListComponent,
                resolve  : {
                    tasks    : ContactsResolver,
                    countries: ContactsCountriesResolver
                },
                children : [
                    {
                        path         : ':id',
                        component    : ContactsDetailsComponent,
                        resolve      : {
                            task     : ContactsContactResolver,
                            countries: ContactsCountriesResolver
                        },
                        canDeactivate: [CanDeactivateContactsDetails]
                    }
                ]
            }
        ]*/
    }
];
