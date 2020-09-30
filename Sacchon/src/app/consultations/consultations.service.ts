import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consultations } from './consultations';

@Injectable({
  providedIn: 'root'
})
export class ConsultationsService {
  readonly url = 'http://localhost:9000/v1/';
  constructor(private http: HttpClient){}

  getConsultations(): Observable<Consultations[]>{
    return this.http.get<Consultations[]>(
      this.url + 'consultations' ,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

  getDoctrorConsultations(id, fromDate, untilDate): Observable<Consultations[]>{
   

    return this.http.get<Consultations[]>(
      this.url + 'doctor/'  + id +  '/consultation/' + fromDate +'/' + untilDate,                
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

  addConsultation(values, id):Observable<any>{
    //location.reload();
    console.log(values.get('medicationName').value, values.get('dosage').value);
    return this.http.post(this.url+ 'add-consultation/' +id,
      {
        "medicationName": values.get('medicationName').value,
        "dosage": values.get('dosage').value
      },
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      })
    }

    getConsultationToUpdate(id):Observable <Consultations> {
      console.log("getConsultationToUpdate " +this.url+ 'consultation/'  +id )
      return this.http.get<Consultations>(
        this.url+ 'consultation/'  +id,
        {headers:new HttpHeaders(
          {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
          )
        }
        );
    }

    updateConsultation(id,data): Observable<any>{
      console.log("updateConsultation method" )
      console.log(id )
     
      return this.http.put( this.url+ 'consultation/'  +id, data,{headers : new  HttpHeaders(
        {'Authorization': 'Basic ' +  btoa(sessionStorage.getItem("credentials"))}
        )});
  
    }

}
