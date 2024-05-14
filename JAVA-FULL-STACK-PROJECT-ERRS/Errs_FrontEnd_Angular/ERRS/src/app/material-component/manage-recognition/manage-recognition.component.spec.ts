import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageRecognitionComponent } from './manage-recognition.component';

describe('ManageRecognitionComponent', () => {
  let component: ManageRecognitionComponent;
  let fixture: ComponentFixture<ManageRecognitionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageRecognitionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageRecognitionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
