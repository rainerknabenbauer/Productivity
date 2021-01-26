<script>
  import { createEventDispatcher, onMount } from "svelte";
  const dispatch = createEventDispatcher();
  
  let symbol;
  let isTaskDetailsVisible = false;

  function showTaskDetails() {
    updateSymbol();
    dispatch("showTaskDetails");
  }

  onMount(() => {
    updateSymbol();
  })

  function updateSymbol() {
    if (isTaskDetailsVisible) {
      symbol = "★";
    } else {
      symbol = "☆";
    }
    isTaskDetailsVisible = !isTaskDetailsVisible;
  }
</script>

<style>
  .btn-flip {
    opacity: 1;
    outline: 0;
    color: #fff;
    line-height: 38px;
    position: relative;
    text-align: center;
    letter-spacing: 1px;
    display: inline-block;
    text-decoration: none;
    font-family: "Open Sans";
    text-transform: uppercase;
    min-width: 132pt;
    border: 0px solid transparent;
    border-radius: 25px;
    box-shadow: rgba(0, 0, 0, 0.1) 0px 2px 5px 0px;
  }
  .btn-flip:hover:after {
    opacity: 1;
    transform: translateY(0) rotateX(0);
  }
  .btn-flip:hover:before {
    opacity: 0;
    transform: translateY(50%) rotateX(90deg);
  }
  .btn-flip:after {
    top: 0;
    left: 0;
    opacity: 0;
    width: 100%;
    color: #323237;
    display: block;
    transition: 0.5s;
    position: absolute;
    background: #f1f1f1;
    content: attr(data-back);
    transform: translateY(-50%) rotateX(90deg);
  }
  .btn-flip:before {
    top: 0;
    left: 0;
    opacity: 1;
    color: #323237;
    display: block;
    padding: 0 30px;
    line-height: 38px;
    transition: 0.5s;
    position: relative;
    background: #999999;
    content: attr(data-front);
    transform: translateY(0) rotateX(0);
  }

  .float {
    float: left;
    margin-right: 25px;
  }

  .left {
    margin-left: 5px;
  }
</style>

<a href="#"
  class="btn-flip float left"
  data-front="add note"
  data-back={symbol}
  on:click={showTaskDetails} />