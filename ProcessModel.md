# Tuition Reimbursement Managemnt System

## Applying for Reimbursement
Must be submitted 1 week prior to start of event.

**Requirements**:
* basic employee information
  * name
  * etc...
* date -> `LocalDate`
* time -> `LocalTime`
* location -> `String`
* description -> `String`
* cost -> `Double`
* grading format -> `Enum?`

* type of event -> `Enum?`
* work-related justification -> `String`


**(Optional)**:

	Note: The projected reimbursement should be provided as a read-only field.
	The project is backend however...
* passing grade
* event-related attachments of *.pdf*, *.png*, *.jpeg*, *.txt*, or *.doc* file type, 
* attachments of approvals already provided of *.msg* (Outlook Email File) file type and type of approval, work time that will be missed 

### Types of events
* `UNIVERSITY_COURSE` -> 0.8
* `SEMINAR` -> 0.6
* `CERTIFICATION_PREPARATION_CLASS` -> 0.75
* `CERTIFICATION` -> 1.0
* `TECHNICAL_TRAINING` -> 0.9
* `OTHER` -> 0.3

### Grading formats
Supplied or defaulted
* `LETTER` -> A B C D F
* `PASS_FAIL` -> PASS / FAIL
* `OUT_OF_TEN` -> 10 9 8 7 6 5 4 3 2 1
* `PERFORMANCE_BASED` -> Requires presentation upload