

document.addEventListener('DOMContentLoaded', () => {
      const agree = document.getElementById('agree');
      const proceedBtn = document.getElementById('proceedBtn');
      const form = document.getElementById('infoForm');

      // Enable button if checkbox checked
      agree.addEventListener('change', () => {
        proceedBtn.disabled = !agree.checked;
      });
    });