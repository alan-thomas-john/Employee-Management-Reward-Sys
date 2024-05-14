import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AddRecognitionComponent } from './add-recognition.component';
import { RecognitionService } from 'src/app/services/recognition.service';

describe('AddRecognitionComponent', () => {
  let component: AddRecognitionComponent;
  let fixture: ComponentFixture<AddRecognitionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddRecognitionComponent ],
      imports: [ HttpClientTestingModule ],  // Include HttpClientTestingModule
      providers: [ RecognitionService ]   
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddRecognitionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
