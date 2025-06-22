import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestApiComponent } from './test-api.component';

describe('TestApiComponent', () => {
  let component: TestApiComponent;
  let fixture: ComponentFixture<TestApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TestApiComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
