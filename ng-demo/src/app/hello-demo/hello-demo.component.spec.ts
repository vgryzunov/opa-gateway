import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelloDemo } from './hello-component-demo.component';

describe('HelloDemoComponent', () => {
  let component: HelloDemo;
  let fixture: ComponentFixture<HelloDemo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HelloDemo ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HelloDemo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
