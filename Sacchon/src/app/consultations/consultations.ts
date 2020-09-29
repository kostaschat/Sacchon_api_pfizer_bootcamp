export interface Consultations {
    id: number;
    medicationName: string;
    dosage: number;
    consultationDate: Date;
    doctor_id: number;
    patient_id: number;
}
