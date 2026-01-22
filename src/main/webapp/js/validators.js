function RegisterValidate() {

    document.getElementById("passerr").style.display = "none";
    document.getElementById("namerr").style.display = "none";

    let pattern = /^[\w.!-]{5,20}$/gm;
    let teststring = document.getElementById("username").value;
    let pattern1 = /^(?=.*[A-Z])[\w.!-]{8,}$/;
    let teststring1 = document.getElementById("password").value;
    let valid = pattern.test(teststring);
    let valid1 = pattern1.test(teststring1);
    let returnvalue = true;
    if(!valid){
        document.getElementById("namerr").style.display = "block";
        returnvalue = false;
    }
    if(!valid1){
        document.getElementById("passerr").style.display = "block";
        returnvalue = false;
    }
    return returnvalue;

}

function LoginValidate(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    if(username == "" || username == null || password == "" || password == null){
        document.getElementById("errore").style.display = "block";
        return false;
    }
    else {
        document.getElementById("errore").style.display = "none";
        return true;
    }
}

function CorsoValidate(){

    document.getElementById("namerr").style.display = "none";
    document.getElementById("prezzerr").style.display = "none";
    document.getElementById("contenterr").style.display = "none";

    let pattern = /^[a-zA-Z\s]{5,30}$/;
    let pattern1 = /^(-)?(0|[1-9]\d+)\.\d+$/;
    let teststring = document.getElementById("nome").value;
    let teststring1 = document.getElementById("prezzo").value;
    let teststring2 = document.getElementById("content").value;
    let valid = pattern.test(teststring);
    let valid1 = pattern1.test(teststring1);
    let returnvalue = true;
    if(!valid){
        document.getElementById("namerr").style.display = "block";
        returnvalue = false;
    }
    if(!valid1){
        document.getElementById("prezzerr").style.display = "block";
        returnvalue = false;
    }
    if(teststring2 == "" || teststring2 == null){
        document.getElementById("contenterr").style.display = "block";
        returnvalue = false;
    }
    return returnvalue;

}

function UtenteValidate(){

    document.getElementById("passerr").style.display = "none";
    document.getElementById("namerr").style.display = "none";

    let pattern = /^[\w.!-]{5,20}$/gm;
    let teststring = document.getElementById("username").value;
    let pattern1 = /^(?=.*[A-Z])[\w.!-]{8,}$/;
    let teststring1 = document.getElementById("password").value;
    let valid = pattern.test(teststring);
    let valid1 = pattern1.test(teststring1);
    let returnvalue = true;
    if(!valid){
        document.getElementById("namerr").style.display = "block";
        returnvalue = false;
    }
    if(!valid1){
        document.getElementById("passerr").style.display = "block";
        returnvalue = false;
    }
    return returnvalue;
}

function TagValidate(){

    document.getElementById("namerr").style.display = "none";

    let pattern = /^[a-zA-Z]{3,30}$/;
    let teststring = document.getElementById("nome").value;
    let valid = pattern.test(teststring);
    let returnvalue = true;
    if(!valid){
        document.getElementById("namerr").style.display = "block";
        returnvalue = false;
    }
    return returnvalue;
}