class Task {
    constructor(id, title, shortDescription, longDescription, additionalNotes, endDate, priority, preDependency, postDependency, ui) {
        this.id = id;
		this.title = title;
		this.description = {
			shortDescription: shortDescription,
			longDescription: longDescription,
			additionalNotes: additionalNotes
		};
        this.endDate = endDate;
        this.priority = priority;
        this.preDependency = preDependency;
		this.postDependency = postDependency;
		this.ui = ui;
    }
}

export default Task;