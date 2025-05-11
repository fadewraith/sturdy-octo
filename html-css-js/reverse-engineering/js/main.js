document.addEventListener('DOMContentLoaded', function() {
    // Initialize Feather Icons
    feather.replace();
    
    // Load and render the curriculum
    renderCurriculum();
    
    // Add smooth scrolling
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);
            
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 70,
                    behavior: 'smooth'
                });
            }
        });
    });
    
    // Add event listeners for expand buttons (will be added dynamically)
    document.addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('expand-button')) {
            const content = e.target.previousElementSibling;
            
            if (content.style.maxHeight) {
                content.style.maxHeight = null;
                e.target.textContent = 'Show More';
            } else {
                content.style.maxHeight = content.scrollHeight + 'px';
                e.target.textContent = 'Show Less';
            }
        }
    });
});

function renderCurriculum() {
    const timelineContainer = document.querySelector('.timeline');
    
    // Render each module
    curriculumModules.forEach((module, index) => {
        const isLeft = index % 2 === 0;
        const moduleElement = createModuleElement(module, isLeft ? 'left' : 'right');
        timelineContainer.appendChild(moduleElement);
    });
}

function createModuleElement(module, position) {
    const moduleElement = document.createElement('div');
    moduleElement.className = `timeline-item ${position}`;
    
    const contentElement = document.createElement('div');
    contentElement.className = 'content';
    
    // Module title
    const titleElement = document.createElement('h4');
    titleElement.textContent = module.title;
    contentElement.appendChild(titleElement);
    
    // Add labels
    const labelsContainer = document.createElement('div');
    labelsContainer.className = 'labels';
    
    // Level label
    const levelLabel = document.createElement('span');
    levelLabel.className = `label ${module.level}`;
    levelLabel.textContent = module.level.charAt(0).toUpperCase() + module.level.slice(1);
    labelsContainer.appendChild(levelLabel);
    
    // Type label (software/hardware)
    if (module.type) {
        const typeLabel = document.createElement('span');
        typeLabel.className = `label ${module.type}`;
        typeLabel.textContent = module.type.charAt(0).toUpperCase() + module.type.slice(1);
        labelsContainer.appendChild(typeLabel);
    }
    
    contentElement.appendChild(labelsContainer);
    
    // Module description
    const descElement = document.createElement('p');
    descElement.textContent = module.description;
    contentElement.appendChild(descElement);
    
    // Collapsible content
    const collapsibleContent = document.createElement('div');
    collapsibleContent.className = 'collapsible-content';
    
    // Key concepts
    if (module.concepts && module.concepts.length > 0) {
        const conceptsTitle = document.createElement('h5');
        conceptsTitle.textContent = 'Key Concepts';
        collapsibleContent.appendChild(conceptsTitle);
        
        const conceptsList = document.createElement('ul');
        module.concepts.forEach(concept => {
            const conceptItem = document.createElement('li');
            conceptItem.textContent = concept;
            conceptsList.appendChild(conceptItem);
        });
        collapsibleContent.appendChild(conceptsList);
    }
    
    // Exercises
    if (module.exercises && module.exercises.length > 0) {
        const exercisesTitle = document.createElement('h5');
        exercisesTitle.textContent = 'Practical Exercises';
        collapsibleContent.appendChild(exercisesTitle);
        
        const exercisesList = document.createElement('ul');
        exercisesList.className = 'exercise-list';
        module.exercises.forEach(exercise => {
            const exerciseItem = document.createElement('li');
            exerciseItem.textContent = exercise;
            exercisesList.appendChild(exerciseItem);
        });
        collapsibleContent.appendChild(exercisesList);
    }
    
    // Additional resources
    if (module.resources && module.resources.length > 0) {
        const resourcesTitle = document.createElement('h5');
        resourcesTitle.textContent = 'Additional Resources';
        collapsibleContent.appendChild(resourcesTitle);
        
        const resourcesList = document.createElement('ul');
        module.resources.forEach(resource => {
            const resourceItem = document.createElement('li');
            if (resource.url) {
                const resourceLink = document.createElement('a');
                resourceLink.href = resource.url;
                resourceLink.textContent = resource.title;
                resourceLink.target = "_blank";
                resourceItem.appendChild(resourceLink);
            } else {
                resourceItem.textContent = resource.title;
            }
            resourcesList.appendChild(resourceItem);
        });
        collapsibleContent.appendChild(resourcesList);
    }
    
    contentElement.appendChild(collapsibleContent);
    
    // Add expand button
    const expandButton = document.createElement('button');
    expandButton.className = 'expand-button';
    expandButton.textContent = 'Show More';
    contentElement.appendChild(expandButton);
    
    moduleElement.appendChild(contentElement);
    
    return moduleElement;
}
