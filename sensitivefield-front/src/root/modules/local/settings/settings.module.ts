import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts';
import { MaterialModule } from '../../environment/md.module';
import { DesignSetComponent } from './components/settings.component/design-set/design-set.component';
import { ProfileComponent } from './components/settings.component/profile/profile.component';
import { RegisterComponent } from './components/settings.component/register/register.component';
import { SettingsComponent } from './components/settings.component/settings.component';
import { SoundSetComponent } from './components/settings.component/sound-set/sound-set.component';

@NgModule({
    declarations: [SettingsComponent, SoundSetComponent, RegisterComponent, ProfileComponent, DesignSetComponent],
    imports: [FormsModule, MaterialModule, ChartsModule, CommonModule],
    bootstrap: [SettingsComponent]
})
export class SenttingsModule {}
