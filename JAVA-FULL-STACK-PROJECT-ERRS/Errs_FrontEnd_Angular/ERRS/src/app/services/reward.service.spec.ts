import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RewardService } from './reward.service';

describe('RewardService', () => {
  let service: RewardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Import HttpClientTestingModule
      providers: [RewardService]   
    });
    service = TestBed.inject(RewardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
