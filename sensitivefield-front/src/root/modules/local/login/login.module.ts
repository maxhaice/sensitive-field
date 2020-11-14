import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../environment/md.module';
import { LoginComponent } from './components/login.component/login.component';

@NgModule({
    declarations: [LoginComponent],
    imports: [FormsModule, MaterialModule],
    bootstrap: [LoginComponent]
})
export class LoginModule {}