// // method 1 
// const searchInput = document.getElementById('searchInput');
// const resultsContainer = document.getElementById('results');

// // Sample data (you can replace this with your own data)
// const data = ['apple', 'banana', 'cherry', 'grape', 'orange'];

// function performSearch() {
//     const searchTerm = searchInput.value.toLowerCase();
//     resultsContainer.innerHTML = ''; // Clear existing results

//     if (searchTerm.trim() === '') {
//         return; // No need to display results when input is empty
//     }

//     data.forEach(item => {
//         if (item.toLowerCase().includes(searchTerm)) {
//             const resultItem = document.createElement('div');
//             resultItem.textContent = item;
//             resultsContainer.appendChild(resultItem);

//             // Highlight matching part of the word
//             const highlightedText = item.replace(new RegExp(searchTerm, 'gi'), match => `<span class="highlight">${match}</span>`);
//             resultItem.innerHTML = highlightedText;
//         }
//     });
// }

// searchInput.addEventListener('input', performSearch);

// method 2 - 

// script.js
const searchInput = document.getElementById('searchInput');
const resultsContainer = document.getElementById('results');
let selectedResultIndex = -1; // Initialize with no selection

// Sample data (you can replace this with your own data)
const data = ['apple', 'banana', 'cherry', 'grape', 'orange'];

function performSearch() {
    const searchTerm = searchInput.value.toLowerCase();
    resultsContainer.innerHTML = ''; // Clear existing results
    selectedResultIndex = -1; // Reset selection

    if (searchTerm.trim() === '') {
        return; // No need to display results when input is empty
    }

    data.forEach((item, index) => {
        if (item.toLowerCase().includes(searchTerm)) {
            const resultItem = document.createElement('div');
            resultItem.textContent = item;
            resultItem.classList.add('result-item'); // Add a class for styling
            resultsContainer.appendChild(resultItem);

            // Highlight matching part of the word
            const highlightedText = item.replace(new RegExp(searchTerm, 'gi'), match => `<span class="highlight">${match}</span>`);
            resultItem.innerHTML = highlightedText;

            // Handle keyboard navigation
            resultItem.addEventListener('mouseenter', () => {
                selectedResultIndex = index;
                updateSelection();
            });

            resultItem.addEventListener('click', () => {
                selectResult(item);
            });
        }
    });

    updateSelection();
}

function updateSelection() {
    const resultItems = document.querySelectorAll('.result-item');
    resultItems.forEach((item, index) => {
        if (index === selectedResultIndex) {
            item.classList.add('selected');
        } else {
            item.classList.remove('selected');
        }
    });
}

function selectResult(item) {
    searchInput.value = item;
    resultsContainer.innerHTML = ''; // Clear results after selection
}

searchInput.addEventListener('input', performSearch);

// Handle keyboard events
searchInput.addEventListener('keydown', e => {
    const resultItems = document.querySelectorAll('.result-item');
    if (resultItems.length === 0) return;

    switch (e.key) {
        case 'ArrowUp':
            e.preventDefault();
            selectedResultIndex = Math.max(selectedResultIndex - 1, 0);
            updateSelection();
            break;
        case 'ArrowDown':
            e.preventDefault();
            selectedResultIndex = Math.min(selectedResultIndex + 1, resultItems.length - 1);
            updateSelection();
            break;
        case 'Enter':
            e.preventDefault();
            selectResult(resultItems[selectedResultIndex].textContent);
            break;
    }
});
