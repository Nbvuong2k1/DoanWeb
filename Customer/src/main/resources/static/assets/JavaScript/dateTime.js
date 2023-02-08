function displayDatetime(){
    let d = new Date();
    d = d.toLocaleDateString();
    setTimeout(()=>displayDatetime(),1000);
    document.getElementById("date-time").innerHTML = d;
}