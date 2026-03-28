const DetailItem = ({ label, value }) => {
  return (
    <div>
      <p className="text-sm font-semibold text-blue-800 mb-1">
        {label}
      </p>
      <div className="text-gray-800 text-base">
        {value}
      </div>
    </div>
  );
};

export default DetailItem;
