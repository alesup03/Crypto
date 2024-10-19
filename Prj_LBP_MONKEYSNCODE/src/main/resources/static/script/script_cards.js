//filter button
function toggleFilterMenu() {
    var menu = document.getElementById("filterMenu");
    menu.classList.toggle("active");
}

//function to combine the selected types into a single field
document.getElementById('filter').addEventListener('submit', function(event) {
    // Impedisce l'invio automatico del form
    event.preventDefault();
    var selectedType1 = document.getElementById("types1").value;
    var selectedType2 = document.getElementById("types2").value;
    
    if(selectedType2=="")
        document.getElementById("types").value = selectedType1;
    
    if(selectedType2!=""){
        if (selectedType1 == selectedType2)
            document.getElementById("types").value = selectedType1;

        if(selectedType1=="")
            document.getElementById("types").value = selectedType2;
        
        if(selectedType1!=""){
            selectedType1 += ", "+selectedType2;
            document.getElementById("types").value = selectedType1;
        }
        console.log(selectedType1);
    }
    document.getElementById("types1").value = null;
    document.getElementById("types2").value = null;
    
    this.submit();
});

// used to set the values of the previous search in the form
document.getElementById("name").value = name;
document.getElementById("set").value = set;
document.getElementById("supertype").value = supertype;
document.getElementById("subtypes").value = subtypes;
document.getElementById("rarity").value = rarity;
document.getElementById("sort").value = sort;
if(desc)
    document.getElementById("desc").checked = true;
else document.getElementById("desc").checked = false;
if(owned)
    document.getElementById("owned").checked = true;
else document.getElementById("owned").checked = false;
if(types.length > 10){
    types= types.split(', ');
    document.getElementById("types1").value = types[0];
    document.getElementById("types2").value = types[1];
}
else document.getElementById("types1").value = types;      