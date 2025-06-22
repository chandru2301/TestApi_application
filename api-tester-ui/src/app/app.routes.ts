import { Routes } from '@angular/router';
import { TestApiComponent } from './test-api/test-api.component';

export const routes: Routes = [
  { path: '', component: TestApiComponent },
  { path: '**', redirectTo: '' }
];
