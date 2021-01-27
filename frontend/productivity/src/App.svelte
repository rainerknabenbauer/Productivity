<script>
	import { onMount } from "svelte";
import Button from "./Button.svelte";
	import MainContent from "./MainContent.svelte";

	let tasksPromise = [];
	let isProjectNotFound = false;

	const host = window.location.hostname;
	const uri = "http://" + host + ":8080";
	const self = "http://" + host + ":5000";

	let projectPromise = [];
	let projectId;

	onMount(async () => {
		getUrlParams();
		tasksPromise = getTasks();
		projectPromise = getProject();
	});

	function getUrlParams() {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
        
        if (urlParams.has('id')) {
            projectId = urlParams.get('id');
        }
	}

	async function getProject() {
		let result = [];
		if (projectId === undefined || projectId === "") {
			createProject();
		} else {
			result = await fetch(uri + "/projects/" + projectId)
				.then((response) => (result = response.json()))
				.catch(() => {
					isProjectNotFound = true;
				});
		}
		return result;
	}

	async function createProject() {
		let newProject = await fetch(uri + "/projects/new")
				.then((response) => (response.json()))
				.catch((error) => alert(error));
			reloadPage(newProject.projectId);
	}

	async function getTasks() {
		let result = [];
		if (!(projectId === undefined || projectId === "")) {
			result = await fetch(uri + "/tasks/" + projectId)
				.then((response) => response.json())
				.catch((error) => alert(error));
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
	
</script>

{#if isProjectNotFound}
<div class="wrapper">
	<div class="noProjectFound">No project reference found.<br>Please consider creating a project.</div>
	<Button text="Create project" on:click={createProject} />
</div>
{:else}
	{#await projectPromise then project}
		{#await tasksPromise then tasks}
			<MainContent {project} {tasks} 
				on:saveProject={(event) => saveProject(event.detail.text)}
				on:undoDelete={(event) => reloadPage(event.detail.text)}
				on:saveTask={() => tasksPromise = getTasks()}
			/>
		{/await}
	{/await}
{/if}

<style>
.noProjectFound {
	padding-bottom: 5px;
}
.wrapper {
	padding: 70px 0;
  	text-align: center;
}
</style>
