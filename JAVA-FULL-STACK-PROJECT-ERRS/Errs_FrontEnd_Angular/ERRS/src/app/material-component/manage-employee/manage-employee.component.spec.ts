import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { ManageEmployeeComponent } from './manage-employee.component';
import { UserService } from 'src/app/services/user.service';

describe('ManageEmployeeComponent', () => {
  let component: ManageEmployeeComponent;
  let fixture: ComponentFixture<ManageEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageEmployeeComponent ],
      imports: [ HttpClientModule ], // Ensure HttpClientModule is imported
      providers: [ UserService ] // Provide the UserService if not already provided
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
