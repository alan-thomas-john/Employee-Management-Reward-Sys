import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SignupComponent } from './signup.component';
import { SnackbarService } from '../services/snackbar.service';
import { UserService } from '../services/user.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 
import { of } from 'rxjs';
import { throwError } from 'rxjs';


describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;

  beforeEach(async () => {
    const mockMatSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);
    await TestBed.configureTestingModule({
      declarations: [ SignupComponent ],
      imports: [ HttpClientTestingModule,MatDialogModule,MatToolbarModule,MatIconModule, MatFormFieldModule,
      MatInputModule ,ReactiveFormsModule,BrowserAnimationsModule  ], 
      providers: [
        UserService,
        SnackbarService,
        { provide: MatSnackBar, useValue: mockMatSnackBar } ,// Provide a mock MatSnackBar
        { provide: MatDialogRef, useValue: { close: jasmine.createSpy('close') } }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    fixture.detectChanges();
  });

  it('should have the signup form initialized with all form controls', () => {
    expect(component.signupForm.contains('name')).toBeTrue();
    expect(component.signupForm.contains('email')).toBeTrue();
    expect(component.signupForm.contains('contactNumber')).toBeTrue();
    expect(component.signupForm.contains('password')).toBeTrue();
    expect(component.signupForm.contains('confirmPassword')).toBeTrue();
  });

  
  it('should validate that passwords match', () => {
    const passwordControl = component.signupForm.get('password');
    const confirmPasswordControl = component.signupForm.get('confirmPassword');
  
    passwordControl?.setValue('123456');
    confirmPasswordControl?.setValue('1234567');
    expect(component.validateSubmit()).toBeFalse(); // Passwords do not match
  
    confirmPasswordControl?.setValue('123456');
    expect(component.validateSubmit()).toBeTrue(); // Passwords match
  });



  it('should disable the signup button if form is invalid', () => {
    component.signupForm.controls['name'].setValue('');
    component.signupForm.controls['email'].setValue('invalid-email'); 
    component.signupForm.controls['contactNumber'].setValue(''); 
    component.signupForm.controls['password'].setValue('12345'); 
    component.signupForm.controls['confirmPassword'].setValue('12345'); 

    // Manually marking the controls as dirty
   component.signupForm.controls['name'].markAsDirty();
   component.signupForm.controls['contactNumber'].markAsDirty();
   component.signupForm.controls['email'].markAsDirty();
   component.signupForm.controls['password'].markAsDirty();
   component.signupForm.controls['confirmPassword'].markAsDirty();

    fixture.detectChanges();

    console.log('Form Valid:', component.signupForm.valid);
    console.log('Name Valid:', component.signupForm.get('name').valid);
    console.log('Name Errors:', component.signupForm.get('name').errors);
    console.log('Email Valid:', component.signupForm.get('email').valid);
    console.log('Email Errors:', component.signupForm.get('email').errors);
    console.log('contact number Valid:', component.signupForm.get('contactNumber').valid);
    console.log('contact number Errors:', component.signupForm.get('contactNumber').errors);
    console.log('Password Valid:', component.signupForm.get('password').valid);
    console.log('Password Errors:', component.signupForm.get('password').errors);
    console.log('confirmPassword Valid:', component.signupForm.get('confirmPassword').valid);
    console.log('ConfirmPassword Errors:', component.signupForm.get('confirmPassword').errors);
  
  
    const button = fixture.debugElement.nativeElement.querySelector('button[type="submit"]');
    console.log('Button Disabled:', button.disabled);
    expect(button.disabled).toBeTrue(); // Button should be disabled since form is invalid
  });


it('should enable the signup button if form is valid', () => {
  component.signupForm.controls['name'].setValue('admin1');
  component.signupForm.controls['contactNumber'].setValue('9876543210');
  component.signupForm.controls['email'].setValue('admin1@gmail.com');
  component.signupForm.controls['password'].setValue('Password1234');
  component.signupForm.controls['confirmPassword'].setValue('Password1234');

   // Manually marking the controls as dirty
   component.signupForm.controls['name'].markAsDirty();
   component.signupForm.controls['contactNumber'].markAsDirty();
   component.signupForm.controls['email'].markAsDirty();
   component.signupForm.controls['password'].markAsDirty();
   component.signupForm.controls['confirmPassword'].markAsDirty();

  fixture.detectChanges(); // Updating the UI to reflect changes in form validity

  // Logging statements to verify values and validation status
  console.log('Form Valid:', component.signupForm.valid);
  console.log('Name Valid:', component.signupForm.get('name').valid);
  console.log('Name Errors:', component.signupForm.get('name').errors);
  console.log('Email Valid:', component.signupForm.get('email').valid);
  console.log('Email Errors:', component.signupForm.get('email').errors);
  console.log('contact number Valid:', component.signupForm.get('contactNumber').valid);
  console.log('contact number Errors:', component.signupForm.get('contactNumber').errors);
  console.log('Password Valid:', component.signupForm.get('password').valid);
  console.log('Password Errors:', component.signupForm.get('password').errors);
  console.log('confirmPassword Valid:', component.signupForm.get('confirmPassword').valid);
  console.log('ConfirmPassword Errors:', component.signupForm.get('confirmPassword').errors);

  

  const button = fixture.debugElement.nativeElement.querySelector('button[type="submit"]');
  console.log('Button Disabled:', button.disabled);
  expect(button.disabled).toBeFalse(); // Expecting the button to be enabled
});

  it('should call UserService and close dialog on successful form submission', () => {
    const userService = TestBed.inject(UserService);
    spyOn(userService, 'signup').and.returnValue(of({ message: 'Signup Successful' })); // Returns an Observable that immediately completes with an object
  
    component.signupForm.get('name').setValue('admin1');
    component.signupForm.get('email').setValue('admin1@example.com');
    component.signupForm.get('contactNumber').setValue('1234567890');
    component.signupForm.get('password').setValue('123456');
    component.signupForm.get('confirmPassword').setValue('123456');
  
    component.handleSubmit();
    expect(userService.signup).toHaveBeenCalled();
    expect(component.dialogRef.close).toHaveBeenCalled();
  });

  it('should display an error message if the signup fails', () => {
    const errorResponse = { error: { message: 'Signup failed' } };
    const userService = TestBed.inject(UserService); // Getting the UserService from TestBed
    const snackbarService = TestBed.inject(SnackbarService);
    spyOn(userService, 'signup').and.returnValue(throwError(() => errorResponse)); // Using throwError to simulate an error response
    spyOn(snackbarService, 'openSnackBar'); // Spying on the openSnackBar method of snackbarService
  
    component.handleSubmit(); // Trigger the handleSubmit method which attempts to sign up
  
    //  to check if the appropriate actions were taken after the error
    fixture.detectChanges(); // Triggering change detection to update the view with the error message
    
    // Ensuring that the snackbar is opened with the correct message
    expect(snackbarService.openSnackBar).toHaveBeenCalledWith('Signup failed', 'error');
  });

 
  it('should inspect the form validity', () => {
    component.signupForm.controls['email'].setValue('admin1@gmail.com');
    fixture.detectChanges();
  
    console.log('Form Validity:', component.signupForm.valid);
    console.log('Form Errors:', component.signupForm.errors);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
