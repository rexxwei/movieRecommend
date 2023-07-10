
function autocomplete(inp, arr) {
  /*the autocomplete function takes two arguments,
  the text field element and an array of possible autocompleted values:*/
  var currentFocus;
  /*execute a function when someone writes in the text field:*/
  inp.addEventListener("input", function(e) {
      var a, b, i, val = this.value;
      /*close any already open lists of autocompleted values*/
      closeAllLists();
      if (!val) { return false;}
      currentFocus = -1;
      /*create a DIV element that will contain the items (values):*/
      a = document.createElement("DIV");
      a.setAttribute("id", this.id + "autocomplete-list");
      a.setAttribute("class", "autocomplete-items");
      /*append the DIV element as a child of the autocomplete container:*/
      this.parentNode.appendChild(a);
      /*for each item in the array...*/
      for (i = 0; i < arr.length; i++) {
        /*check if the item starts with the same letters as the text field value:*/
        if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
          /*create a DIV element for each matching element:*/
          b = document.createElement("DIV");
          /*make the matching letters bold:*/
          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
          b.innerHTML += arr[i].substr(val.length);
          /*insert a input field that will hold the current array item's value:*/
          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
          /*execute a function when someone clicks on the item value (DIV element):*/
          b.addEventListener("click", function(e) {
              /*insert the value for the autocomplete text field:*/
              inp.value = this.getElementsByTagName("input")[0].value;
              /*close the list of autocompleted values,
              (or any other open lists of autocompleted values:*/
              closeAllLists();
          });
          a.appendChild(b);
        }
      }
  });
  /*execute a function presses a key on the keyboard:*/
  inp.addEventListener("keydown", function(e) {
      var x = document.getElementById(this.id + "autocomplete-list");
      if (x) x = x.getElementsByTagName("div");
      if (e.keyCode == 40) {
        /*If the arrow DOWN key is pressed,
        increase the currentFocus variable:*/
        currentFocus++;
        /*and and make the current item more visible:*/
        addActive(x);
      } else if (e.keyCode == 38) { //up
        /*If the arrow UP key is pressed,
        decrease the currentFocus variable:*/
        currentFocus--;
        /*and and make the current item more visible:*/
        addActive(x);
      } else if (e.keyCode == 13) {
        /*If the ENTER key is pressed, prevent the form from being submitted,*/
        e.preventDefault();
        if (currentFocus > -1) {
          /*and simulate a click on the "active" item:*/
          if (x) x[currentFocus].click();
        }
      }
  });
  function addActive(x) {
    /*a function to classify an item as "active":*/
    if (!x) return false;
    /*start by removing the "active" class on all items:*/
    removeActive(x);
    if (currentFocus >= x.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = (x.length - 1);
    /*add class "autocomplete-active":*/
    x[currentFocus].classList.add("autocomplete-active");
  }
  function removeActive(x) {
    /*a function to remove the "active" class from all autocomplete items:*/
    for (var i = 0; i < x.length; i++) {
      x[i].classList.remove("autocomplete-active");
    }
  }
  function closeAllLists(elmnt) {
    /*close all autocomplete lists in the document,
    except the one passed as an argument:*/
    var x = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < x.length; i++) {
      if (elmnt != x[i] && elmnt != inp) {
        x[i].parentNode.removeChild(x[i]);
      }
    }
  }
  /*execute a function when someone clicks in the document:*/
  document.addEventListener("click", function (e) {
      closeAllLists(e.target);
  });
}

/*An array containing all the country names in the world:*/

var countries = ['Avatar', 'Avengers: Age of Ultron', 'Alice in Wonderland', 'A Christmas Carol', 'Alice Through the Looking Glass', "Pirates of the Caribbean: At World's End", "Pirates of the Caribbean: Dead Man's Chest", 'Pirates of the Caribbean: On Stranger Tides', 'Prince of Persia: The Sands of Time', 'Pacific Rim', 'Spectre', 'Spider-Man 3', 'Superman Returns', 'Skyfall', 'Spider-Man 2', 'The Dark Knight Rises', 'Tangled', 'The Lone Ranger', 'The Chronicles of Narnia: Prince Caspian', 'The Avengers', 'John Carter', 'Jurassic World', 'Jack the Giant Slayer', 'Jupiter Ascending', 'Jason Bourne', 'Harry Potter and the Half-Blood Prince', 'Hugo', 'How to Train Your Dragon', 'Harry Potter and the Order of the Phoenix', 'Harry Potter and the Goblet of Fire', 'Batman v Superman: Dawn of Justice', 'Battleship', 'Brave', 'Big Hero 6', 'Batman Begins', 'Quantum of Solace', 'Quest for Camelot', 'Queen of the Damned', 'Quigley Down Under', 'Quills', 'Man of Steel', 'Men in Black 3', 'Monsters University', 'Monsters vs Aliens', 'Maleficent', 'Robin Hood', 'Rush Hour 3', 'Ratatouille', 'Rise of the Guardians', 'Rango', 'King Kong', 'Kung Fu Panda 2', 'Kung Fu Panda 3', 'Kung Fu Panda', 'Knight and Day', 'Captain America: Civil War', 'Cars 2', 'Captain America: The Winter Soldier', 'Charlie and the Chocolate Factory', 'Captain America: The First Avenger', 'Iron Man 3', 'Indiana Jones and the Kingdom of the Crystal Skull', 'Iron Man', 'Inside Out', 'Iron Man 2', 'X-Men: The Last Stand', 'X-Men: Days of Future Past', 'X-Men: Apocalypse', 'X-Men: First Class', 'X-Men Origins: Wolverine', 'Oz: The Great and Powerful', 'Oblivion', "Ocean's Twelve", "Ocean's Eleven", "Ocean's Thirteen", 'Green Lantern', 'G.I. Joe: The Rise of Cobra', 'Guardians of the Galaxy', 'G-Force', 'Ghostbusters', 'Furious 7', 'Frozen', 'Flushed Away', 'Fun with Dick and Jane', 'Final Fantasy: The Spirits Within', 'World War Z', 'WALL·E', 'Wild Wild West', 'Waterworld', 'Wreck-It Ralph', 'Up', 'Unstoppable', 'Unbreakable', 'Underworld: Awakening', 'Unbroken', 'Evan Almighty', 'Edge of Tomorrow', 'Exodus: Gods and Kings', 'Elysium', "Ender's Game", 'Dawn of the Planet of the Apes', 'Dark Shadows', 'Die Another Day', 'Dinosaur', "Dante's Peak", 'Night at the Museum: Battle of the Smithsonian', 'Noah', 'Night at the Museum: Secret of the Tomb', 'Night at the Museum', 'National Treasure', 'Lethal Weapon 4', 'Life of Pi', 'Live Free or Die Hard', 'Little Fockers', "Legend of the Guardians: The Owls of Ga'Hoole", 'Valkyrie', 'Volcano', 'Vertical Limit', 'Vanilla Sky', "Valentine's Day", "You Don't Mess with the Zohan", 'Yogi Bear', "You've Got Mail", 'Year One', 'You, Me and Dupree', 'Zodiac', 'Zookeeper', 'Zathura: A Space Adventure', 'Zero Dark Thirty', 'Zoolander 2'];

/*initiate the autocomplete function on the "myInput" element, and pass along the countries array as possible autocomplete values:*/
autocomplete(document.getElementById("inputName"), countries);
