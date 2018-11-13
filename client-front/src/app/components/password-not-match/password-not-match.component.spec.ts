import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordNotMatchComponent } from './password-not-match.component';

describe('PasswordNotMatchComponent', () => {
  let component: PasswordNotMatchComponent;
  let fixture: ComponentFixture<PasswordNotMatchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordNotMatchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordNotMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
