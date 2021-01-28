<script>
  import { createEventDispatcher } from "svelte";
  const dispatch = createEventDispatcher();

  export let project;

  let isTitleBeingEdited = false;

  function showReminder() {
    dispatch("showReminder");
  }

  function showFAQ() {
    dispatch("showFAQ");
  }

  function showTrashbin() {
    dispatch("showTrashbin");
  }

  function showHistory() {
    dispatch("showHistory");
  }

  function showFirstSteps() {
    dispatch("showFirstSteps");
  }

  function showPinboard() {
    dispatch("showPinboard");
  }

  function editTitle() {
    isTitleBeingEdited = !isTitleBeingEdited;
  }

  function saveProject() {
    editTitle();
    document.title = project.name;
    dispatch('saveProject', {
			text: JSON.stringify(project)
		});
  }

  function showTaskDetails() {
    dispatch("showTaskDetails");
  }
</script>


<header class="header">

    <div class="edit" on:click={showTaskDetails}>
      <img src="./img/edit.png" alt="Add new task">
    </div>
    

    {#if isTitleBeingEdited}
      <input class="editName" type="text" id="fname" name="fname" bind:value={project.name}/>
      <button on:click={saveProject}>OK</button>
    {:else}
      <div class="projectName" on:click={editTitle}>{project.name}</div>
    {/if}
    
    <div class="actionItems" on:click={showFAQ}>
      <i class="fa fa-angle-down" />
    </div>

    <div class="actionItems" on:click={showTrashbin}>
      <i class="fa fa-trash-o" />
    </div>

    <!--
    <button class="w3-button w3-white w3-hide-small actionItems" on:click={showHistory}>
      <i class="fa fa-heartbeat w3-margin-right" />History</button>
    -->

    <div class="actionItems" on:click={showFirstSteps}>
      <i class="fa fa-question-circle-o" />
    </div>

    <div class="actionItems" on:click={showReminder}>
      {#if project.email === null}
      <i class="fa fa-bell-o" />
      {:else}
      <i class="fa fa-bell" />
      {/if}
    </div>

    <div class="actionItems">
      <i class="fa fa-home" on:click={showPinboard}/>
    </div>
  </header>


<style>
  .edit {
    float: left;
    padding: 1px 48px 0 36px;
    height: 32px;
    opacity: 0.8;
  }
  .edit:hover, .actionItems:hover {
    opacity: 0.6;
    border-radius: 50%;
    text-align: center;
  }

  .actionItems {
    float: right;
    padding: 4px 24px 0 24px;
    color: #636363;

    font-size: 16pt;
    text-align: center;
    letter-spacing: 1px;
    text-decoration: none;
    font-family: "Open Sans";
  }

  .projectName {
    font-family: 'Tangerine', cursive;
    font-size: 2em;
    color: #636363;
    float: left;
    margin-top: -2px;
  }
  .editName {
    height: 40px;
  }
  .header {
    height: 50px;
    padding-top: 5px;
    min-width: 500px;
    border: 1px solid transparent;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.03) 0px 2px 5px 0px;
    background-color: #bdcad9;
    background-image: linear-gradient(315deg, #bdcad9 0%, #e1dada 74%);

  }
</style>
