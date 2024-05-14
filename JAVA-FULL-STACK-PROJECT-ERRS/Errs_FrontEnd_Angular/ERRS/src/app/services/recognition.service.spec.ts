import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RecognitionService } from './recognition.service';

describe('RecognitionService', () => {
  let service: RecognitionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Import HttpClientTestingModule
      providers: [RecognitionService]    
    });
    service = TestBed.inject(RecognitionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
