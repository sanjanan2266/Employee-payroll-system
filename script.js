function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    if (username === "admin" && password === "1234") {
        document.getElementById("login").style.display = "none";
        document.getElementById("dashboard").style.display = "block";
        loadEmployees();
    } else {
        alert("Invalid Username or Password");
    }
}

function addEmployee() {

    let name = document.getElementById("name").value;
    let salary = parseFloat(document.getElementById("salary").value);

    if (!name || isNaN(salary)) {
        alert("Enter all details");
        return;
    }

    let hra = salary * 0.20;
    let da = salary * 0.10;
    let gross = salary + hra + da;

    let employee = {
        name: name,
        salary: salary,
        hra: hra,
        da: da,
        gross: gross
    };

    let employees = JSON.parse(localStorage.getItem("employees")) || [];
    employees.push(employee);
    localStorage.setItem("employees", JSON.stringify(employees));

    loadEmployees();

    document.getElementById("name").value = "";
    document.getElementById("salary").value = "";
}

function loadEmployees() {

    let employees = JSON.parse(localStorage.getItem("employees")) || [];

    let table = document.getElementById("employeeTable");
    table.innerHTML = "";

    employees.forEach(emp => {

        table.innerHTML += `
        <tr>
            <td>${emp.name}</td>
            <td>${emp.salary}</td>
            <td>${emp.hra}</td>
            <td>${emp.da}</td>
            <td>${emp.gross}</td>
        </tr>`;
    });
}
function generatePayslip() {

    let employees = JSON.parse(localStorage.getItem("employees")) || [];

    if (employees.length === 0) {
        alert("No employees found!");
        return;
    }

    let emp = employees[employees.length - 1];

    document.getElementById("payslip").innerHTML = `
        <h3>Employee Payslip</h3>
        <p><b>Name:</b> ${emp.name}</p>
        <p><b>Basic Salary:</b> ₹${emp.salary}</p>
        <p><b>HRA (20%):</b> ₹${emp.hra}</p>
        <p><b>DA (10%):</b> ₹${emp.da}</p>
        <p><b>Gross Salary:</b> ₹${emp.gross}</p>
    `;
}