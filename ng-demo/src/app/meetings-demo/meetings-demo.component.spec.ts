import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetingsDemoComponent } from './meetings-demo.component';

describe('MeetingsDemoComponent', () => {
  let component: MeetingsDemoComponent;
  let fixture: ComponentFixture<MeetingsDemoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeetingsDemoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetingsDemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
