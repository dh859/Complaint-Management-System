import React from "react";

const Contact = () => {
  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center p-6">
      <div className="max-w-5xl w-full bg-white rounded-2xl shadow-lg overflow-hidden grid md:grid-cols-2">
        {/* LEFT – INFO SECTION */}
        <div className="bg-linear-to-br from-blue-700 to-blue-900 text-white p-8 flex flex-col justify-center">
          <h2 className="text-3xl font-bold mb-4">Get in Touch</h2>
          <p className="text-blue-100 mb-6">
            Have questions, feedback, or need support? Our team is always ready
            to help you.
          </p>

          <div className="space-y-4 text-sm">
            <p>
              📧 <span className="font-medium">support@cms.com</span>
            </p>
            <p>
              📞 <span className="font-medium">+91 98765 43210</span>
            </p>
            <p>
              📍 <span className="font-medium">India</span>
            </p>
          </div>
        </div>

        {/* RIGHT – FORM SECTION */}
        <div className="p-8">
          <h1 className="text-2xl font-bold text-gray-800 mb-2">Contact Us</h1>
          <p className="text-gray-500 mb-6">
            Fill out the form and we’ll get back to you shortly.
          </p>

          <form className="space-y-5">
            {/* Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Name
              </label>
              <input
                type="text"
                placeholder="Enter your name"
                className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
              />
            </div>

            {/* Email */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Email
              </label>
              <input
                type="email"
                placeholder="Enter your email"
                className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
              />
            </div>

            {/* Message */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Message
              </label>
              <textarea
                rows="4"
                placeholder="Write your message..."
                className="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
              ></textarea>
            </div>

            {/* Submit */}
            <button
              type="submit"
              className="w-full bg-blue-700 text-white py-2.5 rounded-lg font-medium hover:bg-blue-800 transition"
            >
              Send Message
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Contact;
