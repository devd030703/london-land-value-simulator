const postcodeInput = document.getElementById("postcode");
const zoneTypeSelect = document.getElementById("zoneType");
const landShareInput = document.getElementById("landShare");
const landShareValue = document.getElementById("landShareValue");
const taxRateInput = document.getElementById("taxRate");
const taxRateValue = document.getElementById("taxRateValue");
const calculateBtn = document.getElementById("calculateBtn");
const zonesBtn = document.getElementById("zonesBtn");
const valuationOutput = document.getElementById("valuationOutput");
const zonesOutput = document.getElementById("zonesOutput");

function updateSliderLabel(input, label, digits) {
  label.textContent = Number.parseFloat(input.value).toFixed(digits);
}

function setLoading(output) {
  output.textContent = "Loading...";
}

function setButtonsDisabled(disabled) {
  calculateBtn.disabled = disabled;
  zonesBtn.disabled = disabled;
}

async function fetchJson(url) {
  const response = await fetch(url);
  const data = await response.json();
  return { ok: response.ok, data };
}

async function handleCalculate() {
  const postcode = postcodeInput.value.trim();
  const landShare = landShareInput.value;
  const taxRate = taxRateInput.value;
  const zoneType = zoneTypeSelect.value;

  setButtonsDisabled(true);
  setLoading(valuationOutput);

  const params = new URLSearchParams({ postcode, landShare, taxRate, zoneType });
  const { data } = await fetchJson(`/valuation?${params.toString()}`);
  valuationOutput.textContent = JSON.stringify(data, null, 2);

  setButtonsDisabled(false);
}

async function handleZones() {
  const postcode = postcodeInput.value.trim();

  setButtonsDisabled(true);
  setLoading(zonesOutput);

  const params = new URLSearchParams({ postcode });
  const { data } = await fetchJson(`/zones?${params.toString()}`);
  zonesOutput.textContent = JSON.stringify(data, null, 2);

  setButtonsDisabled(false);
}

landShareInput.addEventListener("input", () => {
  updateSliderLabel(landShareInput, landShareValue, 2);
});

taxRateInput.addEventListener("input", () => {
  updateSliderLabel(taxRateInput, taxRateValue, 3);
});

calculateBtn.addEventListener("click", handleCalculate);
zonesBtn.addEventListener("click", handleZones);

postcodeInput.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    event.preventDefault();
    handleCalculate();
  }
});

updateSliderLabel(landShareInput, landShareValue, 2);
updateSliderLabel(taxRateInput, taxRateValue, 3);
