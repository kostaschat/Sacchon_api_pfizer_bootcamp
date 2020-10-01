export interface Consultations {
    id: number;
    medicationName: string;
    dosage: number;
    consultationDate: Date;
    advice: string;
    doctor_id: number;
    patient_id: number;
    uri: string;
}
