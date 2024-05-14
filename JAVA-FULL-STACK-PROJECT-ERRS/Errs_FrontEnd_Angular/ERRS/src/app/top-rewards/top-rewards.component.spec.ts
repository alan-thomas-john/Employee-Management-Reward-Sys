import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopRewardsComponent } from './top-rewards.component';

describe('TopRewardsComponent', () => {
  let component: TopRewardsComponent;
  let fixture: ComponentFixture<TopRewardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopRewardsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopRewardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
