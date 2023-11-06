
const icon = document.getElementById("icon");

icon.addEventListener("click",function (){
    if (icon.classList.contains("fa-caret-right")){
        icon.classList.remove("fa-caret-right");
        icon.classList.add("fa-caret-down");

    }else {
        icon.classList.remove("fa-caret-down");
        icon.classList.add("fa-caret-right")
    }
})




