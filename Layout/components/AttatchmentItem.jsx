import { useEffect, useState } from "react";
import {FileAPI} from "../../API/newapi/FileApi";

const IMAGE_TYPES = ["image/png", "image/jpeg", "image/jpg", "image/webp"];

const AttachmentItem = ({ file }) => {
  const [url, setUrl] = useState(null);
  const [type, setType] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let objectUrl;

    const load = async () => {
      try {
        const blob = await FileAPI.getFile(file.fileId);

        if (!(blob instanceof Blob)) {
          throw new Error("Returned value is not a Blob");
        }

        objectUrl = URL.createObjectURL(blob);
        setUrl(objectUrl);
        setType(blob.type);
      } catch (err) {
        console.error("Attachment load failed", err);
      } finally {
        setLoading(false);
      }
    };

    load();

    return () => {
      if (objectUrl) {
        URL.revokeObjectURL(objectUrl);
      }
    };
  }, [file.fileId]);

  if (loading) {
    return (
      <div className="p-4 border rounded-lg text-gray-500">
        Loading {file.name}...
      </div>
    );
  }

  const isImage = IMAGE_TYPES.includes(type);

  return (
    <div className="border rounded-xl p-4 space-y-3">
      <div className="font-medium text-gray-800">{file.name}</div>

      {isImage ? (
        <img
          src={url}
          alt={file.name}
          className="max-h-64 rounded-lg border object-contain"
        />
      ) : (
        <a
          href={url}
          download={file.name}
          className="inline-flex items-center px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
        >
          Download
        </a>
      )}
    </div>
  );
};

export default AttachmentItem;
