import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  error_msg_duration = 3; // in seconds
  conversion_categories = new FormControl();
  conversion_categories_list: string[];
  current_category_selection;

  category_measure_grouping;

  metric_system_measurements = new FormControl();
  metric_system_measurements_list: string[];
  current_metric_system_measurements_selection;

  imperial_system_measurements = new FormControl();
  imperial_system_measurements_list: string[];
  current_imperial_system_measurements_selection;


  metricMeasurementVal = new FormControl();
  imperialMeasurementVal = new FormControl();

  constructor(private _snackBar: MatSnackBar, private _appService: AppService) {

  }
  ngOnInit(): void {
    this.setup_categories();
  }


  setup_categories() {
    this._appService.getCategories().subscribe((data: any) => {
      this.conversion_categories_list = [...Object.keys(data.categories)]
      this.category_measure_grouping = data.categories
      console.log(this.category_measure_grouping);
      this.setup_defaults()
    })
  }

  setup_defaults() {
    this.current_category_selection = this.conversion_categories_list[0];
    this.setup_measurements()
  }

  setup_measurements() {
    this.reset_previous_selection()
    let cur_cat = this.category_measure_grouping[this.current_category_selection];
    this.metric_system_measurements_list = [...cur_cat.metric.map(m => m.name)];
    this.imperial_system_measurements_list = [...cur_cat.imperial.map(m => m.name)];
    this.current_metric_system_measurements_selection = this.metric_system_measurements_list[0];
  }

  reset_previous_selection(){
    this.imperialMeasurementVal.setValue(null);
    this.metricMeasurementVal.setValue(null);
    this.current_metric_system_measurements_selection=null;
    this.current_imperial_system_measurements_selection=null;
  }

  conversion(convertFrom) {
    let curVal;
    switch (convertFrom) {
      case "metricToImperial":
        curVal = this.metricMeasurementVal.value;
        break;
      case "imperialToMetric":
        curVal = this.imperialMeasurementVal.value; break;
      default:
        curVal = this.metricMeasurementVal.value; break;
    }
    if (curVal == null || curVal == undefined) {
      this._snackBar.open("No value Entered for", '', {
        duration: this.error_msg_duration * 1000,
        panelClass: ['mat-toolbar', 'mat-warn']
      });
      return
    }
    let category = this.current_category_selection;
    let metricSystem = this.current_metric_system_measurements_selection;
    let imperialSystem = this.current_imperial_system_measurements_selection;
    if(metricSystem == null || metricSystem == undefined){
      this._snackBar.open("Missing Metric System Selection", '', {
        duration: this.error_msg_duration * 1000,
        panelClass: ['mat-toolbar', 'mat-warn']
      });
      return
    }
    if(imperialSystem == null || imperialSystem == undefined){
      this._snackBar.open("Missing Imperial System Selection", '', {
        duration: this.error_msg_duration * 1000,
        panelClass: ['mat-toolbar', 'mat-warn']
      });
      return
    }
    if (metricSystem == imperialSystem) {
      this._snackBar.open("Metric and Imperial System(s) are same", '', {
        duration: this.error_msg_duration * 1000,
        panelClass: ['mat-toolbar', 'mat-warn']
      });
      return
    }
    

    this._appService.convertValue(category, metricSystem, imperialSystem, curVal).subscribe(data => {
      switch (convertFrom) {
        case "metricToImperial":
          this.imperialMeasurementVal.setValue(data);
          break;
        case "imperialToMetric":
          this.metricMeasurementVal.setValue(data);
          break;
      }
    })
  }
}
