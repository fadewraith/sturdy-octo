// student-promotion.component.ts
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';

// Interface for individual student data
interface Student {
  id: number; // Unique student identifier
  name: string; // Student name including class-section
  selected: boolean; // Selection state of the student
}

// Interface for class-section details
interface ClassSection {
  classId: number; // Unique identifier for the class
  className: string; // Class number (e.g., "1", "2")
  sectionId: number; // Unique identifier for the section
  sectionName: string; // Section letter (e.g., "A", "B")
}

// Interface for promotion data structure
interface PromotionData {
  fromClass: ClassSection; // Source class-section
  toClass: ClassSection; // Destination class-section
  studentsSelected: { studentName: string; studentId: number }[]; // List of promoted students
}

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
})
export class AppComponent implements OnInit {
  promotionForm: FormGroup; // Reactive form for class selection
  // Array of all class-section combinations
  classes: ClassSection[] = [
    { classId: 1, className: '1', sectionId: 1, sectionName: 'A' },
    { classId: 1, className: '1', sectionId: 2, sectionName: 'B' },
    { classId: 1, className: '1', sectionId: 3, sectionName: 'C' },
    { classId: 2, className: '2', sectionId: 1, sectionName: 'A' },
    { classId: 2, className: '2', sectionId: 2, sectionName: 'B' },
    { classId: 2, className: '2', sectionId: 3, sectionName: 'C' },
    { classId: 3, className: '3', sectionId: 1, sectionName: 'A' },
    { classId: 3, className: '3', sectionId: 2, sectionName: 'B' },
    { classId: 3, className: '3', sectionId: 3, sectionName: 'C' },
    { classId: 4, className: '4', sectionId: 1, sectionName: 'A' },
    { classId: 4, className: '4', sectionId: 2, sectionName: 'B' },
    { classId: 4, className: '4', sectionId: 3, sectionName: 'C' },
    { classId: 5, className: '5', sectionId: 1, sectionName: 'A' },
    { classId: 5, className: '5', sectionId: 2, sectionName: 'B' },
    { classId: 5, className: '5', sectionId: 3, sectionName: 'C' },
  ];
  students: Student[] = []; // Current list of students for selection
  savedPromotions: PromotionData[] = []; // Stored promotion data
  showTable = false; // Controls table visibility
  selectedStudentsCount = 0; // Number of selected students

  constructor(private fb: FormBuilder) {
    // Initialize reactive form with default values
    this.promotionForm = this.fb.group({
      fromClass: ['Select from class'],
      toClass: ['Select to class'],
    });
  }

  ngOnInit() {}

  // Helper method to format class-section display name
  getClassDisplayName(classSection: ClassSection): string {
    return `${classSection.className} (${classSection.sectionName})`;
  }

  // Triggered when toClass dropdown changes
  onToClassChange(event: Event) {
    const formValue = this.promotionForm.value;
    if (
      formValue.fromClass !== 'Select from class' &&
      formValue.toClass !== 'Select to class'
    ) {
      this.showTable = true;
      this.loadStudents(formValue.fromClass);
      this.updateSelectedCount();
    } else {
      this.showTable = false;
      this.students = [];
      this.selectedStudentsCount = 0;
    }
  }

  // Load students for the selected fromClass, excluding already promoted ones
  loadStudents(selectedClassName: string) {
    const selectedClass = this.classes.find(
      (c) => this.getClassDisplayName(c) === selectedClassName
    );
    if (!selectedClass) return;

    const existingPromotions = this.savedPromotions.filter(
      (p) =>
        p.fromClass.classId === selectedClass.classId &&
        p.fromClass.sectionId === selectedClass.sectionId
    );

    if (existingPromotions.length > 0) {
      const promotedStudentIds = new Set<number>();
      existingPromotions.forEach((promo) => {
        promo.studentsSelected.forEach((student) =>
          promotedStudentIds.add(student.studentId)
        );
      });

      this.students = Array(10)
        .fill(null)
        .map((_, index) => ({
          id: index + 1,
          name: `Student ${index + 1} - ${this.getClassDisplayName(
            selectedClass
          )}`,
          selected: false,
        }))
        .filter((student) => !promotedStudentIds.has(student.id));
    } else {
      this.students = Array(10)
        .fill(null)
        .map((_, index) => ({
          id: index + 1,
          name: `Student ${index + 1} - ${this.getClassDisplayName(
            selectedClass
          )}`,
          selected: false,
        }));
    }
  }

  // Toggle all students selection and update count
  toggleAllStudents(event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    this.students.forEach((student) => (student.selected = checked));
    this.updateSelectedCount();
  }

  // Update the count of selected students
  updateSelectedCount() {
    this.selectedStudentsCount = this.students.filter((s) => s.selected).length;
  }

  // Handle individual student selection change
  onStudentSelectionChange(event: Event) {
    this.updateSelectedCount();
    // Check if all students are still selected to update Select All checkbox
    const allSelected = this.students.every((student) => student.selected);
    const selectAllCheckbox = (event.target as HTMLInputElement)
      .closest('table')
      ?.querySelector('thead input[type="checkbox"]') as HTMLInputElement;
    if (selectAllCheckbox) {
      selectAllCheckbox.checked = allSelected;
    }
  }

  // Promote selected students to the toClass
  promoteStudents() {
    const formValue = this.promotionForm.value;
    if (
      formValue.fromClass === 'Select from class' ||
      formValue.toClass === 'Select to class'
    ) {
      alert('Please select both classes');
      return;
    }

    const selectedStudents = this.students.filter((s) => s.selected);
    if (selectedStudents.length === 0) {
      alert('Please select at least one student');
      return;
    }

    const fromClass = this.classes.find(
      (c) => this.getClassDisplayName(c) === formValue.fromClass
    )!;
    const toClass = this.classes.find(
      (c) => this.getClassDisplayName(c) === formValue.toClass
    )!;

    const promotionData = selectedStudents.map((s) => ({
      studentName: s.name,
      studentId: s.id,
    }));

    const existingPromotionIndex = this.savedPromotions.findIndex(
      (p) =>
        p.fromClass.classId === fromClass.classId &&
        p.fromClass.sectionId === fromClass.sectionId &&
        p.toClass.classId === toClass.classId &&
        p.toClass.sectionId === toClass.sectionId
    );

    if (existingPromotionIndex !== -1) {
      this.savedPromotions[existingPromotionIndex].studentsSelected.push(
        ...promotionData
      );
    } else {
      const newPromotion: PromotionData = {
        fromClass: { ...fromClass },
        toClass: { ...toClass },
        studentsSelected: promotionData,
      };
      this.savedPromotions.push(newPromotion);
    }

    console.log(
      'Current savedPromotions structure after promotion:',
      JSON.stringify(this.savedPromotions, null, 2)
    );

    this.promotionForm.reset({
      fromClass: 'Select from class',
      toClass: 'Select to class',
    });
    this.showTable = false;
    this.selectedStudentsCount = 0;
  }

  // Submit all promotions and log final structure
  submitPromotions() {
    console.log(
      'Final submitted promotions:',
      JSON.stringify(this.savedPromotions, null, 2)
    );
  }

  // Remove a promotion entry
  removePromotion(index: number) {
    const removedClass = this.savedPromotions[index].fromClass;
    this.savedPromotions.splice(index, 1);

    const formValue = this.promotionForm.value;
    if (
      formValue.fromClass === this.getClassDisplayName(removedClass) &&
      formValue.toClass !== 'Select to class'
    ) {
      this.loadStudents(formValue.fromClass);
    }
  }
}
