import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRecognitionComponent } from './edit-recognition.component';

describe('EditRecognitionComponent', () => {
  let component: EditRecognitionComponent;
  let fixture: ComponentFixture<EditRecognitionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditRecognitionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditRecognitionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
