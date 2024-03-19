import { ChangeDetectorRef, Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { FuseAlertType } from '@fuse/components/alert';
import { GoogleLoginProvider, SocialAuthService, SocialUser } from 'angularx-social-login';
import { AuthService } from 'app/core/auth/auth.service';

import { User } from 'app/core/user/user.types';

import { InventoryService } from 'app/modules/admin/apps/ecommerce/inventory/inventory.service';

@Component({
    selector     : 'auth-sign-in',
    templateUrl  : './sign-in.component.html',
    encapsulation: ViewEncapsulation.None,
    animations   : fuseAnimations
})
export class AuthSignInComponent implements OnInit
{
    @ViewChild('signInNgForm') signInNgForm: NgForm;

    alert: { type: FuseAlertType; message: string } = {
        type   : 'success',
        message: ''
    };
    signInForm: FormGroup;
    showAlert: boolean = false;
    socialUser: SocialUser;
    user: User;

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private _authService: AuthService,
        private settingService: InventoryService,
        private _formBuilder: FormBuilder,
        private _router: Router,
        private authService: SocialAuthService,
        private _changeDetectorRef: ChangeDetectorRef,
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
        // Create the form
        this.signInForm = this._formBuilder.group({
            email     : ['hughes.brian@company.com', [Validators.required, Validators.email]],
            password  : ['admin', Validators.required],
            rememberMe: ['']
        });

        this.user = {
            id    : 'cfaad35d-07a3-4447-a6c3-d8c3d54fd5df',
            name  : 'Kevin Sanchez Gomez',
            email : 'hughes.brian@company.com',
            avatar: 'assets/images/avatars/brian-hughes.jpg',
            status: 'online'
        };
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Sign in
     */
    signIn(): void
    {
        // Return if the form is invalid
        if ( this.signInForm.invalid )
        {
            return;
        }

        // Disable the form
        this.signInForm.disable();

        // Hide the alert
        this.showAlert = false;

        // Sign in
        this._authService.signIn(this.signInForm.value)
            .subscribe(
                () => {

                    // Set the redirect url.
                    // The '/signed-in-redirect' is a dummy url to catch the request and redirect the user
                    // to the correct page after a successful sign in. This way, that url can be set via
                    // routing file and we don't have to touch here.
                    const redirectURL = this._activatedRoute.snapshot.queryParamMap.get('redirectURL') || '/signed-in-redirect';

                    // Navigate to the redirect url
                    this._router.navigateByUrl(redirectURL);

                },
                (response) => {

                    // Re-enable the form
                    this.signInForm.enable();

                    // Reset the form
                    this.signInNgForm.resetForm();

                    // Set the alert
                    this.alert = {
                        type   : 'error',
                        message: 'Wrong email or password'
                    };

                    // Show the alert
                    this.showAlert = true;
                }
            );
    }

    signInWithGoogle(): void {
        this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
            (data) => {
                this.socialUser= data;
                const host = this.socialUser.email.split('@').pop();
                
                this.user.id = this.socialUser.idToken;
                this.user.name = this.socialUser.firstName+' '+this.socialUser.lastName;
                this.user.email = this.socialUser.email;
                //this.user.email = "asilva@unsa.edu.pe";
                this.user.avatar = this.socialUser.photoUrl;
                this.user.status = 'online';
                this.settingService.getRolByEmail(
                    this.user.email
                ).subscribe((response: any) => {
                   
                    if(response !=null){
                        console.log("email_response: ",response);
                        this.user.roles=response.roles;
                        this.user.id=response.id;
                        localStorage.setItem('user',JSON.stringify(this.user));
                        console.log('user',JSON.stringify(this.user));
                        this._changeDetectorRef.markForCheck();
                        // Sign in

                        /* if(host!=='unsa.edu.pe' && (this.user.rol!==3 && this.user.rol!==6)){
                            this.authService.signOut();
                            return;
                        } */
                        this._authService.signInGoogle(this.user)
                        .subscribe(
                            () => {

                                // Set the redirect url.
                                // The '/signed-in-redirect' is a dummy url to catch the request and redirect the user
                                // to the correct page after a successful sign in. This way, that url can be set via
                                // routing file and we don't have to touch here.
                                const redirectURL = this._activatedRoute.snapshot.queryParamMap.get('redirectURL') || '/signed-in-redirect';

                                // Navigate to the redirect url
                                this._router.navigateByUrl(redirectURL);

                            },
                            (response) => {

                            }
                        );
                    }
                });
                
                
            }
        );
    }
}
