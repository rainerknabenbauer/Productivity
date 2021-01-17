<script>
	import Filter from './Filter.svelte';
    import RotatingBlock from './RotatingBlock.svelte';
</script>

<style>
	.grey {
		background-color: #dddddd;
	}
</style>

<header class="grey">
    <div class="w3-section w3-bottombar w3-padding-16">
	  <RotatingBlock on:showTaskDetails/>
      <Filter on:showReminder />
    </div>
  </header>