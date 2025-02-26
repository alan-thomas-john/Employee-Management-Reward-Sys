import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { PurchaseHistoryComponent } from './purchase-history.component';
import { RewardService } from 'src/app/services/reward.service';

describe('PurchaseHistoryComponent', () => {
  let component: PurchaseHistoryComponent;
  let fixture: ComponentFixture<PurchaseHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchaseHistoryComponent ],
      imports: [ HttpClientTestingModule ],  // Include HttpClientTestingModule
      providers: [ RewardService ] 
    })
    .compileComponents();

    fixture = TestBed.createComponent(PurchaseHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
