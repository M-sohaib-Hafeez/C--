// Donation amount selection
document.addEventListener('DOMContentLoaded', function() {
    const amountButtons = document.querySelectorAll('.amount-btn');
    const customAmountInput = document.getElementById('custom_amount');
    
    amountButtons.forEach(button => {
        button.addEventListener('click', function() {
            const amount = this.getAttribute('data-amount');
            customAmountInput.value = amount;
            
            // Remove active class from all buttons
            amountButtons.forEach(btn => btn.classList.remove('active'));
            
            // Add active class to clicked button
            this.classList.add('active');
        });
    });
    
    // Form validation for donation form
    const donationForm = document.querySelector('.donation-form form');
    if(donationForm) {
        donationForm.addEventListener('submit', function(e) {
            const donorName = document.getElementById('donor_name').value;
            const donorEmail = document.getElementById('donor_email').value;
            
            if(!donorName || !donorEmail) {
                e.preventDefault();
                alert('Please fill in all required fields.');
            }
        });
    }
    
    // Form validation for beneficiary form
    const beneficiaryForm = document.querySelector('.beneficiary-form form');
    if(beneficiaryForm) {
        beneficiaryForm.addEventListener('submit', function(e) {
            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const reason = document.getElementById('reason').value;
            
            if(!name || !email || !reason) {
                e.preventDefault();
                alert('Please fill in all required fields.');
            }
        });
    }
});