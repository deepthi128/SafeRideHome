/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function validateAndCorrectPhoneNumber(textFieldId) {
    var phoneNumber = document.getElementById(textFieldId).value;
    var phoneNumLen = phoneNumber.length;
    for (var i = 0; i < phoneNumLen; i++) {
        if (isNaN(phoneNumber.charAt(i))) {
            phoneNumber = phoneNumber.slice(0, i) + phoneNumber.slice(i + 1, phoneNumLen);
            i--;
            phoneNumLen = phoneNumber.length;
        }
    }
    phoneNumber = '('+phoneNumber.slice(0, 3) +') ' +phoneNumber.slice(3, 6) +'-'+phoneNumber.slice(6, 10);
    document.getElementById(textFieldId).value = phoneNumber;

}


function validateUniversityEmail(emailId){
    
}

function checkPasswordsToMatch(pswrd, cnfrmPswrd) {
    if (document.getElementById(pswrd).value != document.getElementById(cnfrmPswrd).value) {
        alert("Passwords does not match! Please try again!");
        document.getElementById(pswrd).value = document.getElementById(cnfrmPswrd).value = '';
    }
    return;
}