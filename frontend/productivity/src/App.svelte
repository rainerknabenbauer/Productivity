<script>
	import { onMount } from "svelte";
	import MoveableBlock from "./MoveableBlock.svelte";
	import TaskDetails from "./TaskDetails.svelte";
	import Task from "./Task.js";
	import QRcode from "./QRcode.svelte";
	import Reminder from "./Reminder.svelte";
	import Canvas from "./Canvas.svelte";
	import ActionItems from "./ActionItems.svelte";
	import Faq from "./FAQ.svelte";
	import Trashbin from "./Trashbin.svelte";
	import NotImplementedView from "./NotImplementedView.svelte";
	import FirstSteps from "./FirstSteps.svelte";

	let isTaskDetailsVisible = false;
	let isReminderVisible = false;
	let isFAQvisible = false;
	let isTrashbinVisible = false;
	let isHistoryVisible = false;
	let isFirstStepsVisible = false;
	let tasksPromise = [];
	let task;

	const host = window.location.hostname;
	const uri = "http://" + host + ":8080";
	const self = "http://" + host + ":5000";

	let projectPromise = [];
	let projectId;

	onMount(async () => {
		setUrlParams();
		tasksPromise = getTasks();
		projectPromise = getProject();
	});

	function setUrlParams() {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
        
        if (urlParams.has('id')) {
            projectId = urlParams.get('id');
        }
	}

	async function getProject() {
		let result = [];
		if (projectId === undefined || projectId === "") {
			let newProject = await fetch(uri + "/projects/new")
				.then((response) => (result = response.json()))
				.catch((error) => alert(error));
			reloadPage(newProject.projectId);
		} else {
			result = await fetch(uri + "/projects/" + projectId)
				.then((response) => (result = response.json()))
				.catch((error) => alert(error));
		}

		return result;
	}

	async function getTasks() {
		let result = [];
		if (!(projectId === undefined || projectId === "")) {
			let response = await fetch(uri + "/tasks/" + projectId)
				.then((response) => (result = response.json()))
				.catch((error) => alert(error));
			drawLines(await response);
		}
		return result;
	}

	function saveProject(project) {
		fetch("http://" + host + ":8080/projects/", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: project
        });
	}

	function reloadPage(projectReference) {
		location.assign(self + "/?id=" + projectReference);
	}

	function closeAllViews() {
		isReminderVisible = false;
		isFAQvisible = false;
		isTrashbinVisible = false;
		isHistoryVisible = false;
		isFirstStepsVisible = false;
	}

	function toggleTaskDetailsVisibility() {
		let state = isTaskDetailsVisible;
		closeAllViews();
		isTaskDetailsVisible = !state;
		task = new Task();
	}

	function showReminder() {
		let state = isReminderVisible;
		closeAllViews();
		isReminderVisible = !state;
	}

	function showFAQ() {
		let state = isFAQvisible;
		closeAllViews();
		isFAQvisible = !state;
	}

	function showTrashbin() {
		let state = isTrashbinVisible;
		closeAllViews();
		isTrashbinVisible = !state;
	}

	function showHistory() {
		let state = isHistoryVisible;
		closeAllViews();
		isHistoryVisible = !state;
	}

	function showFirstSteps() {
		let state = isFirstStepsVisible;
		closeAllViews();
		isFirstStepsVisible = !state;
	}

	async function addTask() {
		tasksPromise = getTasks();
		isTaskDetailsVisible = !isTaskDetailsVisible;
	}

	function editTask(taskJson) {
		isTaskDetailsVisible = true;
		task = JSON.parse(taskJson);
	}

	function undoDelete() {
		reloadPage(projectId);
	}

	async function drawLines(tasks) {
		var wrapper = document.getElementById("canvas-wrapper");
		var canvas = document.getElementById("canvas");
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);

		tasks.forEach((task) => {
			if(!task.isDeleted) {
				context.beginPath();
				context.moveTo(
				window.outerWidth / 2,
				window.outerHeight / 2 - wrapper.offsetTop,
				0);

				context.lineTo(
					task.ui.xposition - wrapper.offsetLeft + 175,
					task.ui.yposition - wrapper.offsetTop + 25);

				context.strokeStyle = "#dddddd";
				context.stroke();
				context.closePath();
			}
		});
	}
</script>

<!-- !PAGE CONTENT! -->

	<!-- Header -->

	{#await projectPromise then project}
		<ActionItems
		{project}
		on:showTaskDetails={toggleTaskDetailsVisibility}
		on:showReminder={showReminder}
		on:showFAQ={showFAQ}
		on:showTrashbin={showTrashbin}
		on:showHistory={showHistory}
		on:showFirstSteps={showFirstSteps}
		on:saveProject={(event) => saveProject(event.detail.text)}
		on:showPinboard={closeAllViews}
		/>
	{/await}

	{#if isTaskDetailsVisible}
		<TaskDetails on:refresh={addTask} {task} {projectId} />
	{/if}

	{#await tasksPromise then tasks}
		{#each tasks as task (task.id)}
			{#if !task.isDeleted}
				<MoveableBlock
					{task}
					on:edit={(e) => editTask(e.detail.text)}
					on:move={() => drawLines(tasks)}
					on:deleteTask={() => {
						isTaskDetailsVisible = false;
						drawLines(tasks)
						}
					}
				/>
			{/if}
		{/each}
	{/await}

	{#if isReminderVisible}
		{#await projectPromise then project}
			<Reminder {project} on:showReminder={showReminder} />
		{/await}
	{/if}

	{#if isFAQvisible}
		{#await projectPromise then project}
			<Faq {project} on:undoDelete={undoDelete} />
		{/await}
	{/if}

	{#if isTrashbinVisible}
		{#await tasksPromise then tasks}
			<Trashbin {tasks} on:undoDelete={undoDelete} />
		{/await}
	{/if}

	{#if isHistoryVisible}
			<NotImplementedView />
	{/if}

	{#if isFirstStepsVisible}
		<FirstSteps />
	{/if}


	<!-- End page content -->

<Canvas />

<QRcode />

<style>
</style>
