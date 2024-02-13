<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>

    <style>
        h1{
            text-align: center;
            margin-top: 30px;
        }

        .inputForm{
            display: grid;
            place-items: center;
            margin: 0;
        }

        label{
            font-size: 20px;
            margin: 5px;
        }

        .navigation{
            position: absolute;
            right: 0px;
        }

        a{
            text-decoration: none;
            color: black;
            font-size: 20px;
            margin: 5px;
            padding: 5px;
        }

        a:hover{
            background-color: rgba(117, 106, 116, 0.8);
        }

        td{
            font-size: 20px;
            padding: 15px;
        }

        #button{
            display: block;
            margin: 0 auto;
            width: 100px;
            height: auto;
            font-size: 20px;
            padding: 15px;
        }
    </style>
</head>
<body>

    <div class="navigation">
        <a href="index.jsp">form</a>
        <a href="xml-read">read</a>
        <a href="xml-updateview">update</a>
    </div>

        <h1>Enter Details</h1>

    <div class="inputForm">
        <form id="myForm" action="xml-write" method="post">
            <table>
                <tr>
                    <td><label for="name">Name:</label></td>
                    <td><input type="text" id="name" name="name" required></td>
                    <td><span id="nameValidationMessage"></span></td>
                </tr>
                <tr>
                    <td><label for="email">Email:</label></td>
                    <td><input type="email" id="email" name="email" required></td>
                    <td><span id="emailValidationMessage"></span></td>
                </tr>
                <tr>
                    <td><label for="phone">Phone Number:</label></td>
                    <td><input type="text" id="phone" name="phone" required></td>
                    <td><span id="phoneValidationMessage"></span></td>
                </tr>
                <tr>
                    <td><label for="degree">Degree Program:</label></td>
                    <td><input type="text" id="degree" name="degree" required></td>
                    <td><span id="degreeValidationMessage"></span></td>
                </tr>
                <tr>
                    <td><label for="idNum">Identity Card Number:</label></td>
                    <td><input type="text" id="idNum" name="idNum" required></td>
                    <td><span id="idNumValidationMessage"></span></td>
                </tr>
            </table>
            <input type="submit" id="button" value="Submit">
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const form = document.getElementById('myForm');
            const nameInput = document.getElementById('name');
            const emailInput = document.getElementById('email');
            const phoneInput = document.getElementById('phone');
            const degreeInput = document.getElementById('degree');
            const idNumInput = document.getElementById('idNum');

            const nameValidationMessage = document.getElementById('nameValidationMessage');
            const emailValidationMessage = document.getElementById('emailValidationMessage');
            const phoneValidationMessage = document.getElementById('phoneValidationMessage');
            const degreeValidationMessage = document.getElementById('degreeValidationMessage');
            const idNumValidationMessage = document.getElementById('idNumValidationMessage');

            form.addEventListener('submit', function (event) {
                validateForm();
            });

            // Add event listeners for each input field
            nameInput.addEventListener('input', validateName);
            emailInput.addEventListener('input', validateEmail);
            phoneInput.addEventListener('input', validatePhone);
            degreeInput.addEventListener('input', validateDegree);
            idNumInput.addEventListener('input', validateIdNum);

            function validateForm() {
                validateName();
                validateEmail();
                validatePhone();
                validateDegree();
                validateIdNum();
            }

            function validateName() {
                const isNameValid = isValidNameFormat(nameInput.value.trim());

                if (isNameValid) {
                    nameValidationMessage.textContent = 'valid';
                } else {
                    nameValidationMessage.textContent = 'invalid';
                    event.preventDefault();
                }
            }

            function validateEmail() {
                const isEmailValid = isValidEmailFormat(emailInput.value.trim());

                if (isEmailValid) {
                    emailValidationMessage.textContent = 'valid';
                } else {
                    emailValidationMessage.textContent = 'invalid';
                    event.preventDefault();
                }
            }

            function validatePhone() {
                const isPhoneValid = isValidPhoneFormat(phoneInput.value.trim());

                if (isPhoneValid) {
                    phoneValidationMessage.textContent = 'valid';
                } else {
                    phoneValidationMessage.textContent = 'invalid';
                    event.preventDefault();
                }
            }

            function validateDegree() {
                const isDegreeValid = isValidDegreeFormat(degreeInput.value.trim());

                if (isDegreeValid) {
                    degreeValidationMessage.textContent = 'valid';
                } else {
                    degreeValidationMessage.textContent = 'invalid';
                    event.preventDefault();
                }
            }

            function validateIdNum() {
                const isIdNumValid = isValidIdNumFormat(idNumInput.value.trim());

                if (isIdNumValid) {
                    idNumValidationMessage.textContent = 'valid';
                } else {
                    idNumValidationMessage.textContent = 'invalid';
                    event.preventDefault();
                }
            }

            function isValidNameFormat(name) {
                return name.length > 0 && /[A-z]{3,40}/.test(name);
            }

            function isValidEmailFormat(email) {
                return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
            }

            function isValidPhoneFormat(phone) {
                return /^[0-9]{10}$/.test(phone);
            }

            function isValidDegreeFormat(degree) {
                return degree.length > 0 && /[A-z]{1,40}/.test(degree);
            }

            function isValidIdNumFormat(idNum) {
                return /^[0-9]{12}|[0-9]{11}[x|v]/.test(idNum);
            }
        });
    </script>

</body>
</html>