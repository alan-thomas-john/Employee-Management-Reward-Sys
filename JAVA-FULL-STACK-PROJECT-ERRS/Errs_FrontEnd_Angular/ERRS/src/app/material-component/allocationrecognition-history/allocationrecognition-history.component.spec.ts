import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllocationrecognitionHistoryComponent } from './allocationrecognition-history.component';

describe('AllocationrecognitionHistoryComponent', () => {
  let component: AllocationrecognitionHistoryComponent;
  let fixture: ComponentFixture<AllocationrecognitionHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllocationrecognitionHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllocationrecognitionHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
