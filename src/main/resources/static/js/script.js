// ===================================
// MOBILE MENU FUNCTIONALITY
// ===================================
function toggleMobileMenu() {
    const menu = document.getElementById('mobileMenu');
    menu.classList.toggle('active');
}

// Close mobile menu when clicking outside
document.addEventListener('click', function(event) {
    const menu = document.getElementById('mobileMenu');
    const toggle = document.querySelector('.navbar-toggle');

    if (menu && toggle && !menu.contains(event.target) && !toggle.contains(event.target)) {
        menu.classList.remove('active');
    }
});

// ===================================
// REGISTRATION PAGE
// ===================================
if (document.getElementById('infoForm')) {
    const agree = document.getElementById('agree');
    const proceedBtn = document.getElementById('submit');
    const form = document.getElementById('infoForm');
    const phoneInput = document.getElementById("Phone");
    const PREFIX = "+63";

    agree.addEventListener('change', () => {
        proceedBtn.disabled = !agree.checked;
    });

    phoneInput.value = PREFIX;

    phoneInput.addEventListener("input", (e) => {
        let value = phoneInput.value;

        if (!value.startsWith(PREFIX)) {
            value = PREFIX;
        }

        let digits = value.slice(PREFIX.length).replace(/\D/g, "");
        digits = digits.slice(0, 10);

        let formatted = PREFIX;
        if (digits.length > 0) {
            formatted += " " + digits.slice(0, 3);
        }
        if (digits.length > 3) {
            formatted += " " + digits.slice(3, 6);
        }
        if (digits.length > 6) {
            formatted += " " + digits.slice(6, 10);
        }

        phoneInput.value = formatted;
    });

    phoneInput.addEventListener("keydown", (e) => {
        const cursorPos = phoneInput.selectionStart;
        if (cursorPos <= PREFIX.length + 1 && (e.key === "Backspace" || e.key === "Delete")) {
            e.preventDefault();
        }
    });

    phoneInput.addEventListener("paste", (e) => {
        e.preventDefault();
        const pasted = e.clipboardData.getData("text").replace(/\D/g, "");
        const digits = pasted.slice(-10);

        let formatted = PREFIX;
        if (digits.length > 0) {
            formatted += " " + digits.slice(0, 3);
        }
        if (digits.length > 3) {
            formatted += " " + digits.slice(3, 6);
        }
        if (digits.length > 6) {
            formatted += " " + digits.slice(6, 10);
        }

        phoneInput.value = formatted;
    });

    form.addEventListener('submit', function(e) {
        if (!form.checkValidity()) {
            return;
        }

        proceedBtn.classList.add('loading');
        proceedBtn.disabled = true;

        const cleanPhone = phoneInput.value.replace(/\s/g, '');
        phoneInput.value = cleanPhone;
    });

    const formInputs = form.querySelectorAll('input, select');
    formInputs.forEach((input, index) => {
        input.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();

                if (index === formInputs.length - 1 && !proceedBtn.disabled) {
                    form.requestSubmit();
                } else {
                    const nextInput = formInputs[index + 1];
                    if (nextInput) {
                        nextInput.focus();
                    }
                }
            }
        });
    });

    proceedBtn.addEventListener('mousedown', function() {
        if (!this.disabled) {
            this.style.transform = 'scale(0.98)';
        }
    });

    proceedBtn.addEventListener('mouseup', function() {
        this.style.transform = '';
    });

    proceedBtn.addEventListener('mouseleave', function() {
        this.style.transform = '';
    });
}

// ===================================
// LOGIN PAGE
// ===================================
if (document.getElementById('login')) {
    const form = document.getElementById('login');
    const submitBtn = document.getElementById('open');
    const phoneInput = document.getElementById('Phone');
    const referenceInput = document.getElementById('Reference');

    phoneInput.addEventListener('input', function(e) {
        let value = this.value.replace(/\D/g, '');

        if (value.startsWith('63')) {
            value = value.slice(2);
        }

        if (value.length > 0) {
            let formatted = '+63';
            if (value.length > 0) {
                formatted += ' ' + value.slice(0, 3);
            }
            if (value.length > 3) {
                formatted += ' ' + value.slice(3, 6);
            }
            if (value.length > 6) {
                formatted += ' ' + value.slice(6, 10);
            }
            this.value = formatted;
        }
    });

    referenceInput.addEventListener('input', function(e) {
        this.value = this.value.toUpperCase();
    });

    form.addEventListener('submit', function(e) {
        if (!form.checkValidity()) {
            return;
        }

        submitBtn.classList.add('loading');
        submitBtn.disabled = true;

        const cleanPhone = phoneInput.value.replace(/\s/g, '');
        phoneInput.value = cleanPhone;
    });

    const formInputs = form.querySelectorAll('input');
    formInputs.forEach((input, index) => {
        input.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();

                if (index === formInputs.length - 1 && form.checkValidity()) {
                    form.requestSubmit();
                } else {
                    const nextInput = formInputs[index + 1];
                    if (nextInput) {
                        nextInput.focus();
                    }
                }
            }
        });
    });

    submitBtn.addEventListener('mousedown', function() {
        if (!this.disabled) {
            this.style.transform = 'scale(0.98)';
        }
    });

    submitBtn.addEventListener('mouseup', function() {
        this.style.transform = '';
    });

    submitBtn.addEventListener('mouseleave', function() {
        this.style.transform = '';
    });
}

// ===================================
// MODAL FUNCTIONS (Change Details)
// ===================================
function openModal() {
    const modalOverlay = document.getElementById('modalOverlay');
    if (modalOverlay) {
        modalOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
    }
}

function closeModal() {
    const modalOverlay = document.getElementById('modalOverlay');
    const submitBtn = document.querySelector('.btn-submit');
    if (modalOverlay) {
        modalOverlay.classList.remove('active');
        document.body.style.overflow = 'auto';
        if (submitBtn) {
            submitBtn.classList.remove('loading');
        }
    }
}

function closeModalOnOverlay(event) {
    if (event.target === event.currentTarget) {
        closeModal();
    }
}

// ===================================
// CANCEL MODAL FUNCTIONS
// ===================================
function openCancelModal() {
    const cancelModalOverlay = document.getElementById('cancelModalOverlay');
    if (cancelModalOverlay) {
        cancelModalOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
    }
}

function closeCancelModal() {
    const cancelModalOverlay = document.getElementById('cancelModalOverlay');
    if (cancelModalOverlay) {
        cancelModalOverlay.classList.remove('active');
        document.body.style.overflow = 'auto';
    }
}

function closeCancelModalOnOverlay(event) {
    if (event.target === event.currentTarget) {
        closeCancelModal();
    }
}

// ===================================
// SEATING MODAL FUNCTIONS
// ===================================
function openSeatingModal() {
    const modal = document.getElementById('seatingModalOverlay');
    if (modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
        const seatingNotification = document.getElementById('seatingNotification');
        const mobileMenuNotification = document.getElementById('mobileMenuNotification');
        if (seatingNotification) seatingNotification.style.display = 'inline-block';
        if (mobileMenuNotification) mobileMenuNotification.style.display = 'inline-block';
    }
}

function closeSeatingModal() {
    const modal = document.getElementById('seatingModalOverlay');
    if (modal) {
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }
}

function notificationIcon() {
    const showConfirm = document.getElementById('showConfirm');
    const showConfirmMobile = document.getElementById('showConfirmMobile');
    if (showConfirm) showConfirm.style.display = 'inline-block';
    if (showConfirmMobile) showConfirmMobile.style.display = 'inline-block';
    document.querySelectorAll('.notification-dot').forEach(dot => {
        dot.style.display = 'inline-block';
    });
}

function confirmSeating() {
    const confirmBtn = document.querySelector('.seating-btn-confirm');
    if (confirmBtn) {
        confirmBtn.classList.add('loading');

        setTimeout(() => {
            alert('Seating confirmed! Redirecting to your table...');
            closeSeatingModal();
            confirmBtn.classList.remove('loading');
        }, 1500);
    }
}

function declineSeating() {
    closeSeatingModal();
}

// ===================================
// RESERVATION DATA PAGE SPECIFIC
// ===================================
if (document.getElementById('changeDetailsForm')) {
    const form = document.getElementById('changeDetailsForm');
    const submitBtn = document.querySelector('.btn-submit');
    const phoneInput = document.getElementById('customerPhone');

    // Escape key listener for modals
    document.addEventListener('keydown', function(event) {
        if (event.key === 'Escape') {
            closeModal();
            closeCancelModal();
            closeSeatingModal();
        }
    });

    // Phone input formatting
    if (phoneInput) {
        phoneInput.addEventListener('input', function(e) {
            let value = this.value.replace(/\D/g, '');

            if (value.startsWith('63')) {
                value = value.slice(2);
            }

            if (value.length > 0) {
                let formatted = '+63';
                if (value.length > 0) {
                    formatted += ' ' + value.slice(0, 3);
                }
                if (value.length > 3) {
                    formatted += ' ' + value.slice(3, 6);
                }
                if (value.length > 6) {
                    formatted += ' ' + value.slice(6, 10);
                }
                this.value = formatted;
            }
        });
    }

    // Form submit handler
    form.addEventListener('submit', function(e) {
        if (!form.checkValidity()) {
            return;
        }

        if (submitBtn) {
            submitBtn.classList.add('loading');
        }

        if (phoneInput) {
            const cleanPhone = phoneInput.value.replace(/\s/g, '');
            phoneInput.value = cleanPhone;
        }
    });

    // Button animations
    if (submitBtn) {
        submitBtn.addEventListener('mousedown', function() {
            if (!this.classList.contains('loading')) {
                this.style.transform = 'scale(0.98)';
            }
        });

        submitBtn.addEventListener('mouseup', function() {
            this.style.transform = '';
        });

        submitBtn.addEventListener('mouseleave', function() {
            this.style.transform = '';
        });
    }

    // Seating modal overlay click
    const seatingModalOverlay = document.getElementById('seatingModalOverlay');
    if (seatingModalOverlay) {
        seatingModalOverlay.addEventListener('click', function(event) {
            if (event.target === this) {
                closeSeatingModal();
            }
        });
    }

    // Format phone display
    function formatPhoneDisplay() {
        const phoneEl = document.getElementById('phoneDisplay');
        if (phoneEl) {
            let phone = phoneEl.textContent.replace(/\D/g, '');
            if (phone.startsWith('63')) {
                phone = phone.slice(2);
            }
            if (phone.length === 10) {
                phoneEl.textContent = '+63 ' + phone.slice(0, 3) + ' ' + phone.slice(3, 6) + ' ' + phone.slice(6);
            }
        }
    }

    formatPhoneDisplay();
}