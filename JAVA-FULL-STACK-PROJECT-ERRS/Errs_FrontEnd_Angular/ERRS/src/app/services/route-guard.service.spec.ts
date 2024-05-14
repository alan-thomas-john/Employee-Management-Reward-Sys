import { TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouteGuardService } from './route-guard.service';
import { SnackbarService } from './snackbar.service';

describe('RouteGuardService', () => {
  let service: RouteGuardService;
  let mockMatSnackBar;

  beforeEach(() => {
    mockMatSnackBar = jasmine.createSpyObj('MatSnackBar', ['open']);

    TestBed.configureTestingModule({
       // Provide the mock MatSnackBar and SnackbarService
      providers: [
        RouteGuardService,
        SnackbarService,
        { provide: MatSnackBar, useValue: mockMatSnackBar }
      ]
    });
    service = TestBed.inject(RouteGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
