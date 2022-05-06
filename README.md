# MailSend
1. Download all the files and put in one folder
2. Rename file configMail.properties.test to configMail.properties
3. Open configMail.properties and change all values 
    - smtpadd=smtp.gmail.com
    - smtpprt=465 or 587
    - fromadd=nksinghiitk@gmail.com
    - passwd=xxxxxxxxxxx
 
[// Open courseconfig.properties and change all the values]::
[//  - subject=Your Blab test grade Card]:: 
 [//  - headerm=Email,Midsem,Endsem]::
  [// - filename=markslist.txt]::
  [// - instructormail=singhnk@iitk.ac.in]::
  
4. Add yours marks list file to folder and change the name in courseconfig.properties file.
5. Finally run the command sh sm.sh
6. It ask for four input thats provide freedom to create text file with different  category. The out put looks like this

$ sh sm.sh
******************************************
*                                                        *
*                                                        *
*                                                        *
*  Welcome to Mail Send Program   *
*                                                        *
*                                                        *
*                                                        *
******************************************
## Please provide the some parameters:
# Enter the instructor email
nksinghiitk@gmail.com
# Enter the subject of email
This is Blab Grade card
# Enter the marks file name
markslist.txt
# Enter the Message which you want to give to the student
Do hard work.
