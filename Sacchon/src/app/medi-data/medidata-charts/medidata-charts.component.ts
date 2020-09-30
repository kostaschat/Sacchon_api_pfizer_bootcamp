import { Component, Input, OnInit } from '@angular/core';
import {Chart} from 'node_modules/chart.js'
import { MediData } from '../medi-data';

@Component({
  selector: 'app-medidata-charts',
  templateUrl: './medidata-charts.component.html',
  styleUrls: ['./medidata-charts.component.scss']
})
export class MedidataChartsComponent implements OnInit{

  @Input() carbValues: number[];
  @Input() glucoseValues: number[];
  @Input() dateValues: Date[];
  constructor(){}

  ngOnInit(): void {

    //if user is a doctor
    console.log(this.carbValues[0]);
    //if user is a patient
    var myChart = new Chart("Medidata chart", {
    type: 'line',
    data: {
        labels: this.dateValues,
        datasets: [{
            label: 'Glucose (mg/dl)',
            data: this.glucoseValues,
            backgroundColor: 
               'red'
            ,
            borderColor: [
              'red'
            ], fill:false,
            borderWidth: 3
        }, {
          label: 'Carbs (grams)',
            data: this.carbValues,
            backgroundColor: 
              'blue'
            ,
            borderColor: [
              'blue' 
            ], fill:false,
            borderWidth: 3
        
        }
      
      ]
    },
    options: {
      responsive: true,
      title:{
        display: true,
        text: 'Medidata Line Chart' 
      },
        scales: {
            yAxes: [{
                ticks: {
                  max: 250,
                  stepSize: 20,
                    beginAtZero: true
                }
            }]
        }
    }
});
  }

 

}
