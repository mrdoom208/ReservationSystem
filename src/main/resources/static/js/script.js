      const agree = document.getElementById('agree');
      const proceedBtn = document.getElementById('submit');
      const form = document.getElementById('infoForm');

      // Enable button if checkbox checked
      agree.addEventListener('change', () => {
        proceedBtn.disabled = !agree.checked;
      });

///////////////////////////////////////////////////////
function openModal() {
            document.getElementById('modalOverlay').classList.add('active');
            document.body.style.overflow = 'hidden';
        }

        function closeModal() {
            document.getElementById('modalOverlay').classList.remove('active');
            document.body.style.overflow = 'auto';
        }

        function closeModalOnOverlay(event) {
            if (event.target === event.currentTarget) {
                closeModal();
            }
        }
        document.addEventListener('keydown', function(event) {
                    if (event.key === 'Escape') {
                        closeModal();
                    }
                });
////////////////////////////////////////////////////////////
