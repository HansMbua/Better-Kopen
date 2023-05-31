//submiting the checkbox
var checkboxes = document.getElementsByClassName("myCheckbox");
var checkedCheckboxes = [];
for (var i = 0; i < checkboxes.length; i++) {
    checkboxes[i].addEventListener('change', function() {
        var index = parseInt(this.getAttribute("data-index"));
        if (this.checked) {
            // add checkbox to checked list
            checkedCheckboxes[index] = this.value;
        } else {
            // remove checkbox from checked list
            checkedCheckboxes.splice(index, 1);
        }
        // update hidden field value
        var hiddenField = document.getElementById("checkedCheckboxes");
        hiddenField.value = JSON.stringify(checkedCheckboxes);
        // submit form using AJAX
        var form = document.getElementById("myForm");
        var formData = new FormData(form);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', form.action, true);
        xhr.onload = function() {
            if (xhr.status === 200) {
                console.log('Form submitted successfully');
            }
        };
        xhr.send(formData);
    });
}

// // submiting the checkbox
// var checkboxes = document.getElementsByClassName("myCheckbox");
// var checkedCheckboxes = [];
// for (var i = 0; i < checkboxes.length; i++) {
//     checkboxes[i].addEventListener('change', function() {
//         var index = parseInt(this.getAttribute("data-index"));
//         if (this.checked) {
//             // add checkbox to checked list
//             checkedCheckboxes[index] = this.value;
//         } else {
//             // remove checkbox from checked list
//             checkedCheckboxes.splice(index, 1);
//         }
//         // update hidden field value
//         var hiddenField = document.getElementById("checkedCheckboxes");
//         hiddenField.value = JSON.stringify(checkedCheckboxes);
//         // submit form using AJAX
//         var form = document.getElementById("myForm");
//         var formData = new FormData(form);
//         var xhr = new XMLHttpRequest();
//         xhr.open('POST', form.action, true);
//         xhr.onload = function() {
//             if (xhr.status === 200) {
//                 // update productList with the response from the server
//                 var response = JSON.parse(xhr.responseText);
//                 var productList = document.getElementById("productList");
//                 productList.innerHTML = response.html;
//             }
//         };
//         xhr.send(formData);
//     });
// }



